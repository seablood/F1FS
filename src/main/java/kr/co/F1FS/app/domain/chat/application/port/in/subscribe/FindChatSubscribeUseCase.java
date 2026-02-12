package kr.co.F1FS.app.domain.chat.application.port.in.subscribe;

import java.util.List;

public interface FindChatSubscribeUseCase {
    List<Long> findSubscribeChatRoom(String username);
    String getLastEnterTime(Long roomId, String username);
}
