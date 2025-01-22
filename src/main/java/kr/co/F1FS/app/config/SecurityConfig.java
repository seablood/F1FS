package kr.co.F1FS.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.F1FS.app.config.auth.PrincipalDetailsService;
import kr.co.F1FS.app.config.jwt.filter.TokenFilter;
import kr.co.F1FS.app.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.config.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import kr.co.F1FS.app.config.login.handler.LoginFailureHandler;
import kr.co.F1FS.app.config.login.handler.LoginSuccessHandler;
import kr.co.F1FS.app.config.oauth2.handler.OAuth2FailureHandler;
import kr.co.F1FS.app.config.oauth2.handler.OAuth2SuccessHandler;
import kr.co.F1FS.app.config.oauth2.service.CustomOAuth2UserService;
import kr.co.F1FS.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PrincipalDetailsService principalDetailsService;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/v3/api-docs", "/swagger-resources/**",
                        "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception",
                        "/static/**", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //
        // 추후 프론트 연결을 고려한 CORS 처리 코드 추가 필요
                                                //

        http.formLogin((formLogin) -> formLogin.disable());
        http.httpBasic((httpBasic) -> httpBasic.disable());
        http.csrf((csrf) -> csrf.disable()); // dev 시, 비활성화
        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()));

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests((authorizeRequests) ->
            authorizeRequests
                    .requestMatchers("/api/v1/user/user-info",
                            "/api/v1/follow-driver/**",
                            "/api/v1/post/save",
                            "/api/v1/post/modify/**").authenticated()
                    .anyRequest().permitAll());

        http.oauth2Login((oauth2Login) ->
                oauth2Login
                        .loginPage("/")
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                        .userInfoEndpoint((endpoint) -> endpoint.userService(customOAuth2UserService)));

        // 자체 필터 시큐리티 필터에 추가
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(tokenFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(principalDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler(jwtTokenService, userRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }

    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter(){
        CustomJsonUsernamePasswordAuthenticationFilter filter = new CustomJsonUsernamePasswordAuthenticationFilter(
                objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(loginSuccessHandler());
        filter.setAuthenticationFailureHandler(loginFailureHandler());
        return filter;
    }

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter(jwtTokenService, userRepository, principalDetailsService);
    }
}
