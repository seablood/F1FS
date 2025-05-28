package kr.co.F1FS.app.global.util.exception.authentication;

import org.springframework.security.core.AuthenticationException;

public class AccountTemporaryException extends AuthenticationException {
    public AccountTemporaryException(String message) {
        super(message);
    }
}
