package kr.co.F1FS.app.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.F1FS.app.global.config.admin.filter.AdminJsonUsernamePasswordAuthenticationFilter;
import kr.co.F1FS.app.global.config.admin.handler.AdminLoginFailureHandler;
import kr.co.F1FS.app.global.config.admin.handler.AdminLoginSuccessHandler;
import kr.co.F1FS.app.global.config.auth.PrincipalDetailsService;
import kr.co.F1FS.app.global.config.jwt.filter.TokenFilter;
import kr.co.F1FS.app.global.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.global.config.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import kr.co.F1FS.app.global.config.login.handler.LoginFailureHandler;
import kr.co.F1FS.app.global.config.login.handler.LoginSuccessHandler;
import kr.co.F1FS.app.global.config.login.provider.CustomAuthenticationProvider;
import kr.co.F1FS.app.global.config.oauth2.handler.OAuth2FailureHandler;
import kr.co.F1FS.app.global.config.oauth2.handler.OAuth2SuccessHandler;
import kr.co.F1FS.app.global.config.oauth2.service.CustomOAuth2UserService;
import kr.co.F1FS.app.global.config.oauth2.util.OAuth2CookieRepository;
import kr.co.F1FS.app.domain.repository.rdb.user.UserRepository;
import kr.co.F1FS.app.global.config.redis.RedisConfig;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PrincipalDetailsService principalDetailsService;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2CookieRepository oAuth2CookieRepository;
    private final RedisConfig redisConfig;
    private final RedisHandler redisHandler;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/v3/api-docs", "/swagger-resources/**",
                        "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception",
                        "/static/**", "/favicon.ico", "/swagger-ui/index.html", "/elastic/**");
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
                    .requestMatchers("/api/v1/auth/user-save",
                                    "/api/v1/auth/verify-code",
                                    "/login",
                                    "/api/v1/post/find-all",
                                    "/api/v1/post/find/**",
                                    "/api/v1/driver/**",
                                    "/api/v1/constructor/**",
                                    "/swagger-ui/**",
                                    "/api-docs/**",
                                    "/v3/api-docs/**",
                                    "/swagger-resources/**",
                                    "/webjars/**",
                                    "/swagger/**",
                                    "/api/v1/email/**",
                                    "/resources/templates/**",
                                    "/api/v1/search-driver/**",
                                    "/api/v1/search-post/**",
                                    "/api/v1/search-CD/**",
                                    "/elastic/**",
                                    "/api/v1/fcm/**").permitAll()
                    .anyRequest().authenticated());

        http.oauth2Login((oauth2Login) ->
                oauth2Login
                        .loginPage("/")
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                        .authorizationEndpoint((endpoint) ->
                                endpoint.authorizationRequestRepository(oAuth2CookieRepository))
                        .userInfoEndpoint((endpoint) -> endpoint.userService(customOAuth2UserService)));

        // 자체 필터 시큐리티 필터에 추가
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterAfter(adminJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(tokenFilter(), AdminJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(principalDetailsService);
        return new ProviderManager(provider);
    }

    @Bean AuthenticationManager customAuthenticationManager(){
        return new ProviderManager(customAuthenticationProvider);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler(jwtTokenService, userRepository, redisConfig);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler(redisConfig, redisHandler, objectMapper);
    }

    @Bean
    public AdminLoginSuccessHandler adminLoginSuccessHandler(){
        return new AdminLoginSuccessHandler(jwtTokenService, userRepository, redisConfig);
    }

    @Bean
    public AdminLoginFailureHandler adminLoginFailureHandler(){
        return new AdminLoginFailureHandler(redisConfig, redisHandler);
    }

    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter(){
        CustomJsonUsernamePasswordAuthenticationFilter filter = new CustomJsonUsernamePasswordAuthenticationFilter(
                objectMapper);
        filter.setAuthenticationManager(customAuthenticationManager());
        filter.setAuthenticationSuccessHandler(loginSuccessHandler());
        filter.setAuthenticationFailureHandler(loginFailureHandler());
        return filter;
    }

    @Bean
    public AdminJsonUsernamePasswordAuthenticationFilter adminJsonUsernamePasswordAuthenticationFilter(){
        AdminJsonUsernamePasswordAuthenticationFilter filter = new AdminJsonUsernamePasswordAuthenticationFilter(
                objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(adminLoginSuccessHandler());
        filter.setAuthenticationFailureHandler(adminLoginFailureHandler());
        return filter;
    }

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter(jwtTokenService, userRepository, principalDetailsService);
    }
}
