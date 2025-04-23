package kr.co.F1FS.app.global.config.auth;

import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.global.config.redis.RedisConfig;
import kr.co.F1FS.app.global.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User { // 자체 로그인 & OAuth2 로그인 상세 정보
    private User user;
    private Map<String, Object> attributes;
    private final RedisConfig redisConfig;

    public PrincipalDetails(User user, RedisConfig redisConfig){
        this.user = user;
        this.redisConfig = redisConfig;
    } // 자체 로그인
    public PrincipalDetails(User user, Map<String, Object> attributes, RedisConfig redisConfig){ // OAuth2 로그인
        this.user = user;
        this.attributes = attributes;
        this.redisConfig = redisConfig;
    }

    public User getUser(){
        return this.user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.user.getRole().getKey()));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        if(user.getRole().equals(Role.DORMANT)) return false;
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(redisConfig.redisTemplate().hasKey("login:lock:"+getUsername())) return false;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(user.getRole().equals(Role.TEMPORARY)) return false;
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
