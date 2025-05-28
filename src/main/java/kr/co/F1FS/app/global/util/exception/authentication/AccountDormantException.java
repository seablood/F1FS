package kr.co.F1FS.app.global.util.exception.authentication;

import org.springframework.security.core.AuthenticationException;

public class AccountDormantException extends AuthenticationException {
    public AccountDormantException(String message) {
        super(message);
    }
}
