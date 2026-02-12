package kr.co.F1FS.app.domain.chat.application.service.subscribe;

import kr.co.F1FS.app.domain.chat.application.port.in.subscribe.CheckChatSubscribeUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckChatSubscribeService implements CheckChatSubscribeUseCase {
    private final RedisHandler redisHandler;
    private static final String CHAT_SUB_PREFIX = "chat:room:";

    @Override
    public boolean isSubscribe(Long roomId, String username) {
        String roomKey = CHAT_SUB_PREFIX + roomId;
        return Boolean.TRUE.equals(redisHandler.getSetOperations().isMember(roomKey, username));
    }
}
