package kr.co.F1FS.app.domain.notification.application.service.notice;

import kr.co.F1FS.app.domain.notification.application.port.in.notice.NotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.QueryNotificationUseCase;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationNotificationService implements NotificationUseCase {
    private final QueryNotificationUseCase queryNotificationUseCase;

    @Override
    @Cacheable(value = "NotificationDTOByRedisId", key = "#redisId", cacheManager = "redisLongCacheManager")
    public ResponseNotificationDTO getNotificationByRedisId(Long redisId){
        return queryNotificationUseCase.findByRedisIdForDTO(redisId);
    }

    @Override
    public ResponseNotificationDTO getNotificationById(Long id){
        return queryNotificationUseCase.findByIdForDTO(id);
    }

    @Override
    public Page<SimpleResponseNotificationDTO> getNotificationList(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return queryNotificationUseCase.findAll(pageable);
    }
}
