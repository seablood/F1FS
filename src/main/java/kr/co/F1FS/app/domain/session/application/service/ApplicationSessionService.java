package kr.co.F1FS.app.domain.session.application.service;

import kr.co.F1FS.app.domain.session.application.mapper.SessionMapper;
import kr.co.F1FS.app.domain.session.application.port.in.QuerySessionUseCase;
import kr.co.F1FS.app.domain.session.application.port.in.SessionUseCase;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.sessionresult.application.port.in.QuerySessionResultUseCase;
import kr.co.F1FS.app.global.presentation.dto.session.ResponseSessionDTO;
import kr.co.F1FS.app.global.presentation.dto.sessionresult.ResponseSessionResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationSessionService implements SessionUseCase {
    private final QuerySessionUseCase querySessionUseCase;
    private final QuerySessionResultUseCase querySessionResultUseCase;
    private final SessionMapper sessionMapper;

    @Override
    @Cacheable(value = "SessionDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseSessionDTO getSessionByID(Long id){
        Session session = querySessionUseCase.findById(id);
        List<ResponseSessionResultDTO> resultList = querySessionResultUseCase.getSessionResultBySession(session);

        return sessionMapper.toResponseSessionDTO(session, resultList);
    }
}
