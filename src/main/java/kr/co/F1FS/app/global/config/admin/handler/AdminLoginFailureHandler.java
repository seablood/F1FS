package kr.co.F1FS.app.global.config.admin.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.global.config.redis.RedisConfig;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
public class AdminLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final RedisHandler redisHandler;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String username = (String) request.getAttribute("username");
        String failKey = "login:fail:"+username;
        String lockKey = "login:lock:"+username;

        if(!redisHandler.getRedisTemplate().hasKey(lockKey)){
            Long failCount = redisHandler.getValueOperations().increment(failKey);
            redisHandler.getRedisTemplate().expire(failKey, Duration.ofMinutes(5));

            if(failCount >= 3){
                redisHandler.executeOperations(() -> redisHandler.getValueOperations()
                        .set(lockKey, "locked", Duration.ofMinutes(5)));
                redisHandler.getRedisTemplate().delete(failKey);
                log.warn("관리자 [{}] 계정이 5분간 잠김", username);
            }
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("로그인 실패! 아이디와 비밀번호를 다시 입력해주세요.");
        log.error("로그인 실패 [{}]: {}", username, exception.getMessage());
    }
}
