package kr.co.F1FS.app.domain.suspend.application.service;

import kr.co.F1FS.app.domain.suspend.application.mapper.SuspensionLogMapper;
import kr.co.F1FS.app.domain.suspend.application.port.in.SuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.suspend.infrastructure.repository.SuspensionLogRepository;
import kr.co.F1FS.app.global.presentation.dto.suspend.ResponseSuspensionLogDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuspensionLogService implements SuspensionLogUseCase {
    private final SuspensionLogMapper logMapper;
    private final SuspensionLogRepository suspensionLogRepository;
    private final CacheEvictUtil cacheEvictUtil;

    @Cacheable(value = "SuspensionLogDTO", key = "#user.id", cacheManager = "redisLongCacheManager")
    public ResponseSuspensionLogDTO getSuspensionLog(User user){
        SuspensionLog log = suspensionLogRepository.findBySuspendUser(user)
                .orElseThrow(() -> new IllegalArgumentException("징계 이력이 없습니다."));

        return logMapper.toResponseSuspensionLogDTO(log);
    }

    public void deleteSuspensionLog(User user){
        SuspensionLog log = suspensionLogRepository.findBySuspendUser(user)
                .orElseThrow(() -> new IllegalArgumentException("징계 이력이 없습니다."));
        cacheEvictUtil.evictCachingSuspensionLog(user);

        suspensionLogRepository.delete(log);
    }
}
