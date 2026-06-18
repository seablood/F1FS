package kr.co.F1FS.app.domain.postRoom.application.service;

import kr.co.F1FS.app.domain.postRoom.application.port.in.VerifyPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.VerifyPostRoomDTO;
import kr.co.F1FS.app.global.config.jwt.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyPostRoomService implements VerifyPostRoomUseCase {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenService jwtTokenService;

    @Override
    public boolean verify(VerifyPostRoomDTO dto, PostRoom postRoom) {
        return bCryptPasswordEncoder.matches(dto.getPassword(), postRoom.getPassword());
    }

    @Override
    public boolean validateToken(Long roomId, String token) {
        return jwtTokenService.validatePostRoomToken(token, roomId);
    }
}
