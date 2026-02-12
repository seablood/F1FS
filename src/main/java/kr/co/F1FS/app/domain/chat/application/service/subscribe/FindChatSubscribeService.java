package kr.co.F1FS.app.domain.chat.application.service.subscribe;

import kr.co.F1FS.app.domain.chat.application.port.in.subscribe.FindChatSubscribeUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FindChatSubscribeService implements FindChatSubscribeUseCase {
    private final RedisHandler redisHandler;
    private static final String CHAT_LAST_ENTER_PREFIX = "chat:lastenter";

    @Override
    public List<Long> findSubscribeChatRoom(String username) {
        Set<Object> subscribeList = redisHandler.getSetOperations().members(username);

        return subscribeList
                .stream()
                .map(o -> Long.valueOf(o.toString()))
                .toList();
    }

    @Override
    public String getLastEnterTime(Long roomId, String username) {
        String lastEnterKey = CHAT_LAST_ENTER_PREFIX + roomId + ":" + username;
        return redisHandler.getValueOperations().get(lastEnterKey).toString();
    }
}
