package kr.co.F1FS.app.domain.sessionresult.application.mapper.admin;

import kr.co.F1FS.app.domain.sessionresult.presentation.dto.admin.CreateSessionResultDTO;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.CreateSessionResultCommand;
import org.springframework.stereotype.Component;

@Component
public class AdminSessionResultMapper {
    public CreateSessionResultCommand toCreateSessionResultCommand(CreateSessionResultDTO dto){
        return CreateSessionResultCommand.builder()
                .driverName(dto.getDriverName())
                .constructorName(dto.getConstructorName())
                .position(dto.getPosition())
                .timeOrGap(dto.getTimeOrGap())
                .points(dto.getPoints())
                .isFastestLap(dto.fastestLap())
                .raceStatus(dto.getRaceStatus())
                .build();
    }
}
