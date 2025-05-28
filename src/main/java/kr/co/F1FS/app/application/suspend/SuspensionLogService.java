package kr.co.F1FS.app.application.suspend;

import kr.co.F1FS.app.domain.model.rdb.SuspensionLog;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.suspend.SuspensionLogRepository;
import kr.co.F1FS.app.presentation.suspend.dto.ResponseSuspensionLogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuspensionLogService {
    private final SuspensionLogRepository suspensionLogRepository;

    public void save(SuspensionLog log){
        suspensionLogRepository.save(log);
    }

    public ResponseSuspensionLogDTO getSuspensionLog(User user){
        SuspensionLog log = suspensionLogRepository.findBySuspendUser(user)
                .orElseThrow(() -> new IllegalArgumentException("징계 이력이 없습니다."));

        return ResponseSuspensionLogDTO.toDto(log);
    }

    public void deleteSuspensionLog(User user){
        SuspensionLog log = suspensionLogRepository.findBySuspendUser(user)
                .orElseThrow(() -> new IllegalArgumentException("징계 이력이 없습니다."));

        suspensionLogRepository.delete(log);
    }
}
