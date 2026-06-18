package kr.co.F1FS.app.domain.postRoom.application.port.in;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.VerifyPostRoomDTO;

public interface VerifyPostRoomUseCase {
    boolean verify(VerifyPostRoomDTO dto, PostRoom postRoom);
    boolean validateToken(Long roomId, String token);
}
