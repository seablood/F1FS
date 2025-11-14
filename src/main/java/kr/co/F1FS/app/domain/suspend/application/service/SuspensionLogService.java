package kr.co.F1FS.app.domain.suspend.application.service;

import kr.co.F1FS.app.domain.suspend.application.mapper.SuspensionLogMapper;
import kr.co.F1FS.app.domain.suspend.application.port.in.SuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.application.port.out.SuspensionLogJpaPort;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.suspend.presentation.dto.CreateSuspensionLogCommand;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suspend.ResponseSuspensionLogDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuspensionLogService implements SuspensionLogUseCase {
    private final SuspensionLogMapper logMapper;
    private final SuspensionLogJpaPort suspensionLogJpaPort;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public SuspensionLog save(SuspensionLog log) {
        return suspensionLogJpaPort.save(log);
    }

    @Override
    @Cacheable(value = "SuspensionLogDTO", key = "#user.id", cacheManager = "redisLongCacheManager")
    public ResponseSuspensionLogDTO getSuspensionLog(User user){
        SuspensionLog log = suspensionLogJpaPort.findBySuspendUser(user);

        return logMapper.toResponseSuspensionLogDTO(log);
    }

    @Override
    public void deleteSuspensionLog(User user){
        SuspensionLog log = suspensionLogJpaPort.findBySuspendUser(user);
        cacheEvictUtil.evictCachingSuspensionLog(user);

        suspensionLogJpaPort.delete(log);
    }

    @Override
    public SuspensionLog toSuspensionLog(CreateSuspensionLogCommand command, User user) {
        return logMapper.toSuspensionLog(command, user);
    }
}
