package kr.co.F1FS.app.domain.auth.application.port.in;

import jakarta.servlet.http.HttpServletResponse;

public interface TokenUseCase {
    void createNewAccessToken(Long id, String refreshToken, HttpServletResponse response);
}
