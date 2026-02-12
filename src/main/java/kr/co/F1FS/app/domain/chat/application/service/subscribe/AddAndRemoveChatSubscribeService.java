package kr.co.F1FS.app.domain.chat.application.service.subscribe;

import kr.co.F1FS.app.domain.chat.application.port.in.subscribe.AddAndRemoveChatSubscribeUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddAndRemoveChatSubscribeService implements AddAndRemoveChatSubscribeUseCase {
    private final RedisHandler redisHandler;
    private static final String CHAT_SUB_PREFIX = "chat:room:";
    private static final String CHAT_LAST_ENTER_PREFIX = "chat:lastenter";

    @Override
    public void addSubscriber(Long roomId, String username, LocalDateTime lastEnterTime) {
        String roomKey = CHAT_SUB_PREFIX + roomId;
        redisHandler.executeOperations(() -> redisHandler.getSetOperations().add(roomKey, username));
        redisHandler.executeOperations(() -> redisHandler.getSetOperations().add(username, roomId));

        String now = lastEnterTime.toString();
        String lastEnterKey = CHAT_LAST_ENTER_PREFIX + roomId + ":" + username;
        redisHandler.executeOperations(() -> redisHandler.getValueOperations().set(lastEnterKey, now));
    }

    @Override
    public void removeSubscriber(Long roomId, String username) {
        String roomKey = CHAT_SUB_PREFIX + roomId;
        redisHandler.executeOperations(() -> redisHandler.getSetOperations().remove(roomKey, username));
        redisHandler.executeOperations(() -> redisHandler.getSetOperations().remove(username, roomId));
    }

    @Override
    public void deleteChatRoom(Long roomId) {
        String roomKey = CHAT_SUB_PREFIX + roomId;
        Set<Object> memberList = redisHandler.getSetOperations().members(roomKey);

        if(memberList != null){
            for (Object member : memberList){
                redisHandler.executeOperations(() -> redisHandler.getSetOperations().remove(member.toString(), roomId));
            }
        }

        redisHandler.deleteKey(roomKey);
    }
}
