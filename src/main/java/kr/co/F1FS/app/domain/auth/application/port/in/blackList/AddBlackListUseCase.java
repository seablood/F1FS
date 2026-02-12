package kr.co.F1FS.app.domain.auth.application.port.in.blackList;

public interface AddBlackListUseCase {
    void addBlackList(String token, long expirationMillis);
}
