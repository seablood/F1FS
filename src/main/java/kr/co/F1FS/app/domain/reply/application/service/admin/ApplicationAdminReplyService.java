package kr.co.F1FS.app.domain.reply.application.service.admin;

import kr.co.F1FS.app.domain.reply.application.port.in.admin.AdminReplyUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.QueryReplyUseCase;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListByUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminReplyService implements AdminReplyUseCase {
    private final QueryReplyUseCase queryReplyUseCase;

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseReplyListByUserDTO> findReplyByUser(int page, int size, String condition, Long id) {
        Pageable pageable = switchCondition(page, size, condition);

        return queryReplyUseCase.findAllByUserForDTO(id, pageable);
    }

    @Override
    public Pageable switchCondition(int page, int size, String condition) {
        return switch (condition){
            case "older" ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        };
    }
}
