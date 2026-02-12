package kr.co.F1FS.app.domain.auth.application.port.in.blackList;

public interface CheckBlackListUseCase {
    boolean isBlacklisted(String token);
}
