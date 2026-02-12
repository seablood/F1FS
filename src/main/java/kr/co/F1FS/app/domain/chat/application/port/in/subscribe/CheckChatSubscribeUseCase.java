package kr.co.F1FS.app.domain.chat.application.port.in.subscribe;

public interface CheckChatSubscribeUseCase {
    boolean isSubscribe(Long roomId, String username);
}
