package kr.co.F1FS.app.domain.admin.driver.application.service;

import kr.co.F1FS.app.domain.admin.driver.application.mapper.AdminDriverMapper;
import kr.co.F1FS.app.domain.admin.driver.application.port.in.AdminDriverUseCase;
import kr.co.F1FS.app.domain.admin.driver.application.port.out.AdminDriverCDSearchPort;
import kr.co.F1FS.app.domain.admin.driver.application.port.out.AdminDriverPort;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.AdminResponseDriverDTO;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.ModifyDriverDTO;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.CombinedDriverRequest;
import kr.co.F1FS.app.domain.elastic.application.port.in.CDSearchUseCase;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminDriverService implements AdminDriverUseCase {
    private final DriverUseCase driverUseCase;
    private final CDSearchUseCase searchUseCase;
    private final AdminDriverPort driverPort;
    private final AdminDriverCDSearchPort searchPort;
    private final AdminDriverMapper adminDriverMapper;

    @Override
    @Transactional
    public AdminResponseDriverDTO save(CombinedDriverRequest request) {
        Driver driver = driverPort.save(driverUseCase.save(request.getDriverDTO(), request.getCurrentSeasonDTO(),
                request.getSinceDebutDTO()));
        searchPort.save(searchUseCase.save(driver));
        return adminDriverMapper.toAdminResponseDriverDTO(driver);
    }

    @Override
    public Page<SimpleResponseDriverDTO> findAll(int page, int size, String condition){
        return driverUseCase.findAll(page, size, condition);
    }

    @Override
    public AdminResponseDriverDTO getDriverById(Long id){
        Driver driver = driverPort.findById(id);

        return adminDriverMapper.toAdminResponseDriverDTO(driver);
    }

    @Override
    @Transactional
    public AdminResponseDriverDTO modify(Long id, ModifyDriverDTO dto){
        Driver driver = driverPort.findById(id);
        driverUseCase.modify(driver, adminDriverMapper.toModifyDriverCommand(dto));
        driverPort.saveAndFlush(driver);

        return adminDriverMapper.toAdminResponseDriverDTO(driver);
    }
}
