package kr.co.F1FS.app.domain.complain.reply.application.port.in;

import kr.co.F1FS.app.domain.complain.reply.presentation.dto.CreateReplyComplainDTO;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.ResponseReplyComplainListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyComplainUseCase {
    void save(Long id, User user, CreateReplyComplainDTO dto);
    Page<ResponseReplyComplainListDTO> getReplyComplainListByUser(int page, int size, String condition, User user);
    ResponseReplyComplainDTO getReplyComplainById(Long id);
    void delete(Long id, User user);
    Pageable switchCondition(int page, int size, String condition);
}
