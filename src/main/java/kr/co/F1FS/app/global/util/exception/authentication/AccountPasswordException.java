package kr.co.F1FS.app.global.util.exception.authentication;

import org.springframework.security.core.AuthenticationException;

public class AccountPasswordException extends AuthenticationException {
    public AccountPasswordException(String message) {
        super(message);
    }
}
