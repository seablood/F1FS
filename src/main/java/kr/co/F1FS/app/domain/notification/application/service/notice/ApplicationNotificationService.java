package kr.co.F1FS.app.domain.notification.application.service.notice;

import kr.co.F1FS.app.domain.notification.application.port.in.notice.NotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.QueryNotificationUseCase;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationNotificationService implements NotificationUseCase {
    private final QueryNotificationUseCase queryNotificationUseCase;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "NotificationDTOByRedisId", key = "#redisId", cacheManager = "redisLongCacheManager")
    public ResponseNotificationDTO getNotificationByRedisId(Long redisId){
        return queryNotificationUseCase.findByRedisIdForDTO(redisId);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseNotificationDTO getNotificationById(Long id){
        return queryNotificationUseCase.findByIdForDTO(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SimpleResponseNotificationDTO> getNotificationList(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return queryNotificationUseCase.findAllForDTO(pageable);
    }
}
