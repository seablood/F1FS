package kr.co.F1FS.app.domain.admin.driver.application.service;

import kr.co.F1FS.app.domain.admin.driver.application.mapper.AdminDriverMapper;
import kr.co.F1FS.app.domain.admin.driver.application.port.in.AdminDriverUseCase;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.AdminResponseDriverDTO;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.ModifyDriverDTO;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.CombinedDriverRequest;
import kr.co.F1FS.app.domain.elastic.application.port.in.CDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
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
    private final AdminDriverMapper adminDriverMapper;

    @Override
    @Transactional
    public AdminResponseDriverDTO save(CombinedDriverRequest request) {
        Driver driver = driverUseCase.save(driverUseCase.save(request.getDriverDTO(), request.getCurrentSeasonDTO(),
                request.getSinceDebutDTO()));
        searchUseCase.save(driver);
        return adminDriverMapper.toAdminResponseDriverDTO(driver);
    }

    @Override
    public Page<SimpleResponseDriverDTO> findAll(int page, int size, String condition){
       return driverUseCase.findAll(page, size, condition);
    }

    @Override
    public AdminResponseDriverDTO getDriverById(Long id){
        Driver driver = driverUseCase.findByIdNotDTO(id);

        return adminDriverMapper.toAdminResponseDriverDTO(driver);
    }

    @Override
    @Transactional
    public AdminResponseDriverDTO modify(Long id, ModifyDriverDTO dto){
        Driver driver = driverUseCase.findByIdNotDTO(id);
        DriverDocument document = searchUseCase.findDriverDocumentById(id);

        driverUseCase.modify(driver, adminDriverMapper.toModifyDriverCommand(dto));
        searchUseCase.modify(document, driver);
        driverUseCase.saveAndFlush(driver);
        searchUseCase.save(document);

        return adminDriverMapper.toAdminResponseDriverDTO(driver);
    }
}
