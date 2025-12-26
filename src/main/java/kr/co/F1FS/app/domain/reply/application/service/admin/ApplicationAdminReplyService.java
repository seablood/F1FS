package kr.co.F1FS.app.domain.reply.application.service.admin;

import kr.co.F1FS.app.domain.reply.application.port.in.admin.AdminReplyUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.QueryReplyUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyByUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationAdminReplyService implements AdminReplyUseCase {
    private final QueryReplyUseCase queryReplyUseCase;
    private final QueryUserUseCase queryUserUseCase;

    @Override
    public Page<ResponseReplyByUserDTO> findReplyByUser(int page, int size, String condition, Long id) {
        Pageable pageable = switchCondition(page, size, condition);
        User user = queryUserUseCase.findById(id);

        return queryReplyUseCase.findAllByUser(user, pageable);
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
