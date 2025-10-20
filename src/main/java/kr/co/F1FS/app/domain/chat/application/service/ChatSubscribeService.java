package kr.co.F1FS.app.domain.chat.application.service;

import kr.co.F1FS.app.domain.chat.application.port.in.ChatSubscribeUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatSubscribeService implements ChatSubscribeUseCase {
    private final RedisHandler redisHandler;
    private static final String CHAT_SUB_PREFIX = "chat:room:";
    private static final String CHAT_LAST_ENTER_PREFIX = "chat:lastenter";

    public String getRoomKey(Long roomId){
        return CHAT_SUB_PREFIX + roomId;
    }

    public String getLastEnterKey(Long roomId, String username){
        return CHAT_LAST_ENTER_PREFIX + roomId + ":" + username;
    }

    @Override
    public void addSubscriber(Long roomId, String username, LocalDateTime lastEnterTime){
        redisHandler.executeOperations(() -> redisHandler.getSetOperations().add(getRoomKey(roomId), username));
        redisHandler.executeOperations(() -> redisHandler.getSetOperations().add(username, roomId));

        String now = lastEnterTime.toString();
        redisHandler.executeOperations(() -> redisHandler.getValueOperations().set(getLastEnterKey(roomId, username), now));
    }

    @Override
    public List<Long> findSubscribeChatRoom(String username) {
        Set<Object> subscribeList = redisHandler.getSetOperations().members(username);

        return subscribeList
                .stream()
                .map(o -> Long.valueOf(o.toString()))
                .toList();
    }

    @Override
    public void removeSubscriber(Long roomId, String username){
        redisHandler.executeOperations(() -> redisHandler.getSetOperations().remove(getRoomKey(roomId), username));
        redisHandler.executeOperations(() -> redisHandler.getSetOperations().remove(username, roomId));
    }

    @Override
    public void deleteChatRoom(Long roomId) {
        String key = getRoomKey(roomId);
        Set<Object> memberList = redisHandler.getSetOperations().members(key);

        if(memberList != null){
            for (Object member : memberList){
                redisHandler.executeOperations(() -> redisHandler.getSetOperations().remove(member.toString(), roomId));
            }
        }

        redisHandler.deleteKey(key);
    }

    @Override
    public String getLastEnterTime(Long roomId, String username) {
        return redisHandler.getValueOperations().get(getLastEnterKey(roomId, username)).toString();
    }

    @Override
    public boolean isSubscribe(Long roomId, String username){
        return Boolean.TRUE.equals(redisHandler.getSetOperations().isMember(getRoomKey(roomId), username));
    }
}
