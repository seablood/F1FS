package kr.co.F1FS.app.domain.complain.post.application.service.admin;

import kr.co.F1FS.app.domain.complain.post.application.port.in.admin.AdminPostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.in.QueryPostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.ResponsePostComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.post.ResponsePostComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminPostComplainService implements AdminPostComplainUseCase {
    private final QueryPostComplainUseCase queryPostComplainUseCase;

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostComplainListDTO> getPostComplainAll(int page, int size, String condition) {
        Pageable pageable = switchCondition(page, size, condition);

        return queryPostComplainUseCase.findPostComplainListForDTO(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "PostComplainDTOForAdmin", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponsePostComplainDTO getPostComplainById(Long id) {
        return queryPostComplainUseCase.findByIdForDTO(id);
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
