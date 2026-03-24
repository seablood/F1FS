package kr.co.F1FS.app.domain.session.application.service;

import kr.co.F1FS.app.domain.session.application.mapper.SessionMapper;
import kr.co.F1FS.app.domain.session.application.port.in.QuerySessionUseCase;
import kr.co.F1FS.app.domain.session.application.port.in.SessionUseCase;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.sessionresult.application.port.in.QuerySessionResultUseCase;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.ResponseSessionResultListDTO;
import kr.co.F1FS.app.global.presentation.dto.session.ResponseSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationSessionService implements SessionUseCase {
    private final QuerySessionUseCase querySessionUseCase;
    private final QuerySessionResultUseCase querySessionResultUseCase;
    private final SessionMapper sessionMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "SessionDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseSessionDTO getSessionById(Long id){
        Session session = querySessionUseCase.findById(id);
        List<ResponseSessionResultListDTO> resultList = querySessionResultUseCase.findAllBySessionForDTO(session.getId());

        return sessionMapper.toResponseSessionDTO(session, resultList);
    }
}
