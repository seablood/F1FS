package kr.co.F1FS.app.global.util.exception.authentication;

import kr.co.F1FS.app.global.presentation.dto.suspend.ResponseSuspensionLogDTO;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class AccountSuspendException extends AuthenticationException {
    private final ResponseSuspensionLogDTO dto;

    public AccountSuspendException(String message, ResponseSuspensionLogDTO dto){
        super(message);
        this.dto = dto;
    }
}
