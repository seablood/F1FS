package kr.co.F1FS.app.domain.complain.reply.application.service.admin;

import kr.co.F1FS.app.domain.complain.reply.application.port.in.QueryReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.application.port.in.admin.AdminReplyComplainUseCase;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.SimpleResponseReplyComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationAdminReplyComplainService implements AdminReplyComplainUseCase {
    private final QueryReplyComplainUseCase queryReplyComplainUseCase;

    @Override
    public Page<SimpleResponseReplyComplainDTO> getReplyComplainAll(int page, int size, String condition) {
        Pageable pageable = switchCondition(page, size, condition);

        return queryReplyComplainUseCase.findAllForDTO(pageable);
    }

    @Override
    @Cacheable(value = "ReplyComplainDTOForAdmin", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseReplyComplainDTO getReplyComplainById(Long id) {
        return queryReplyComplainUseCase.findByIdForDTO(id);
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
