package kr.co.F1FS.app.domain.chat.application.port.in.subscribe;

import java.time.LocalDateTime;

public interface AddAndRemoveChatSubscribeUseCase {
    void addSubscriber(Long roomId, String username, LocalDateTime lastEnterTime);
    void removeSubscriber(Long roomId, String username);
    void deleteChatRoom(Long roomId);
}
