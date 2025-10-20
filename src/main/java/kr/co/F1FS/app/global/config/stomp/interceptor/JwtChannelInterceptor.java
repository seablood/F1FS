package kr.co.F1FS.app.global.config.stomp.interceptor;

import kr.co.F1FS.app.global.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.global.util.exception.chat.StompException;
import kr.co.F1FS.app.global.util.exception.chat.StompExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {
    private final JwtTokenService jwtTokenService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(StompCommand.CONNECT.equals(accessor.getCommand())){
            String preToken = accessor.getFirstNativeHeader("Authorization");
            if (preToken != null && preToken.startsWith("Bearer ")) {
                String token = preToken.substring(7);
                if(jwtTokenService.validateToken(token)){
                    String username = jwtTokenService.getUsername(token)
                            .orElseThrow(() -> new StompException(StompExceptionType.JWT_AUTHENTICATED_ERROR));

                    accessor.getSessionAttributes().put("AUTHENTICATED_USERNAME", username);
                    accessor.setUser(new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()));
                }else {
                    throw new StompException(StompExceptionType.JWT_AUTHENTICATED_ERROR);
                }
            }else {
                throw new StompException(StompExceptionType.JWT_AUTHENTICATED_ERROR);
            }
        }else {
            Object username = accessor.getSessionAttributes().get("AUTHENTICATED_USERNAME");
            if(username != null){
                log.info("Authenticated User : {}", username);
            }else {
                throw new StompException(StompExceptionType.JWT_AUTHENTICATED_ERROR);
            }
        }

        return message;
    }
}
