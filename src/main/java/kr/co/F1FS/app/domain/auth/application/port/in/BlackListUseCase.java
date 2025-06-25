package kr.co.F1FS.app.domain.auth.application.port.in;

public interface BlackListUseCase {
    void addBlackList(String token, long expirationMillis);
    boolean isBlacklisted(String token);
}
