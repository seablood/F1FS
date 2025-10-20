package kr.co.F1FS.app.domain.chat.application.port.in;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatSubscribeUseCase {
    void addSubscriber(Long roomId, String username, LocalDateTime lastEnterTime);
    List<Long> findSubscribeChatRoom(String username);
    void removeSubscriber(Long roomId, String username);
    void deleteChatRoom(Long roomId);
    String getLastEnterTime(Long roomId, String username);
    boolean isSubscribe(Long roomId, String username);
}
