package kr.co.F1FS.app.domain.session.application.mapper;

import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.global.presentation.dto.session.ResponseSessionDTO;
import kr.co.F1FS.app.global.presentation.dto.sessionresult.ResponseSessionResultDTO;
import kr.co.F1FS.app.global.util.SessionType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionMapper {
    public Session toSession(SessionType sessionType, String time){
        return Session.builder()
                .sessionType(sessionType)
                .time(time)
                .build();
    }

    public ResponseSessionDTO toResponseSessionDTO(Session session, List<ResponseSessionResultDTO> resultList) {
        return ResponseSessionDTO.builder()
                .id(session.getId())
                .sessionTime(session.getTime())
                .resultList(resultList)
                .build();
    }
}
