package kr.co.F1FS.app.global.config.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.global.config.redis.RedisConfig;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import kr.co.F1FS.app.global.util.exception.authentication.AccountPasswordException;
import kr.co.F1FS.app.global.util.exception.authentication.AccountSuspendException;
import kr.co.F1FS.app.presentation.suspend.dto.ResponseSuspensionLogDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final RedisConfig redisConfig;
    private final RedisHandler redisHandler;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String username = (String) request.getAttribute("username");
        Map<String, Object> result = new HashMap<>();

        if(exception instanceof AccountPasswordException){
            String failKey = "login:fail:"+username;
            String lockKey = "login:lock:"+username;

            if(!redisConfig.redisTemplate().hasKey(lockKey)){
                Long failCount = redisHandler.getValueOperations().increment(failKey);
                redisConfig.redisTemplate().expire(failKey, Duration.ofMinutes(5));

                if(failCount >= 5){
                    redisHandler.executeOperations(() -> redisHandler.getValueOperations()
                            .set(lockKey, "locked", Duration.ofMinutes(5)));
                    redisConfig.redisTemplate().delete(failKey);
                    log.warn("사용자 [{}] 계정이 5분간 잠김", username);
                }
            }

            result.put("error", "로그인 실패! 아이디와 비밀번호를 다시 입력해주세요.");
            result.put("description", "비밀번호 불일치");

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            log.error("로그인 실패 [{}]: {}", username, exception.getMessage());
        } else if (exception instanceof AccountSuspendException) {
            AccountSuspendException ex = (AccountSuspendException) exception;
            ResponseSuspensionLogDTO dto = ex.getDto();

            result.put("error", "계정이 정지되어 사용할 수 없습니다.");
            result.put("nickname", dto.getNickname());
            result.put("durationDays", String.valueOf(dto.getDurationDays()));
            result.put("description", dto.getDescription());
            result.put("paraphrase", dto.getParaphrase());

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            log.error("로그인 실패 [{}]: {}", username, ex.getMessage());
        }
    }
}
