package kr.co.F1FS.app.global.util.exception.authentication;

import org.springframework.security.core.AuthenticationException;

public class AccountLockedException extends AuthenticationException {
    public AccountLockedException(String message) {
        super(message);
    }
}
