package kr.co.F1FS.app.domain.admin.driver.application.service;

import kr.co.F1FS.app.domain.admin.driver.application.port.in.AdminDriverUseCase;
import kr.co.F1FS.app.domain.admin.driver.application.port.out.AdminDriverCDSearchPort;
import kr.co.F1FS.app.domain.admin.driver.application.port.out.AdminDriverPort;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.AdminResponseDriverDTO;
import kr.co.F1FS.app.domain.driver.application.mapper.DriverMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.CombinedDriverRequest;
import kr.co.F1FS.app.domain.elastic.application.port.in.CDSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDriverService implements AdminDriverUseCase {
    private final DriverUseCase driverUseCase;
    private final CDSearchUseCase searchUseCase;
    private final AdminDriverPort driverPort;
    private final AdminDriverCDSearchPort searchPort;
    private final DriverMapper driverMapper;

    @Override
    public AdminResponseDriverDTO save(CombinedDriverRequest request) {
        Driver driver = driverPort.save(driverUseCase.save(request.getDriverDTO(), request.getCurrentSeasonDTO(),
                request.getSinceDebutDTO()));
        searchPort.save(searchUseCase.save(driver));
        return driverMapper.toAdminResponseDriverDTO(driver);
    }
}
