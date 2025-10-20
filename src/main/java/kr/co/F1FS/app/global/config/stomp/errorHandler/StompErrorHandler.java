package kr.co.F1FS.app.global.config.stomp.errorHandler;

import kr.co.F1FS.app.global.util.ExceptionType;
import kr.co.F1FS.app.global.util.exception.chat.StompException;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Component
public class StompErrorHandler extends StompSubProtocolErrorHandler {
    public StompErrorHandler(){
        super();
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        if(ex instanceof StompException stompException){
            ExceptionType exceptionType = stompException.getExceptionType();

            return errorMessage(exceptionType);
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> errorMessage(ExceptionType exceptionType){
        String message = exceptionType.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        accessor.setMessage(String.valueOf(exceptionType.getHttpStatus()));
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(message.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}
