package kr.co.F1FS.app.domain.user.application.port.in;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyDescriptionDTO;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyNicknameDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;

public interface UserUseCase {
    ResponseUserDTO findByNickname(String nickname);
    ResponseUserDTO modifyNickname(User user, ModifyNicknameDTO dto);
    ResponseUserDTO modifyDescription(User user, ModifyDescriptionDTO dto);
}
