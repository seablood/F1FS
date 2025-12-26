package kr.co.F1FS.app.domain.driver.application.service.admin;

import kr.co.F1FS.app.domain.driver.application.mapper.admin.AdminDriverMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.admin.AdminDriverUseCase;
import kr.co.F1FS.app.domain.driver.presentation.dto.admin.AdminResponseDriverDTO;
import kr.co.F1FS.app.domain.driver.presentation.dto.admin.ModifyDriverDTO;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.CreateDriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.QueryDriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.UpdateDriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.admin.CombinedDriverRequest;
import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.CreateCDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.QueryCDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.UpdateCDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchException;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminDriverService implements AdminDriverUseCase {
    private final CreateDriverUseCase createDriverUseCase;
    private final UpdateDriverUseCase updateDriverUseCase;
    private final QueryDriverUseCase queryDriverUseCase;
    private final CreateCDSearchUseCase createCDSearchUseCase;
    private final UpdateCDSearchUseCase updateCDSearchUseCase;
    private final QueryCDSearchUseCase queryCDSearchUseCase;
    private final AdminDriverMapper adminDriverMapper;

    @Override
    @Transactional
    public AdminResponseDriverDTO save(CombinedDriverRequest request) {
        Driver driver = createDriverUseCase.save(request.getDriverDTO(), request.getCurrentSeasonDTO(),
                request.getSinceDebutDTO());
        createCDSearchUseCase.save(driver);
        return adminDriverMapper.toAdminResponseDriverDTO(driver);
    }

    @Override
    public Page<SimpleResponseDriverDTO> findAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

       return queryDriverUseCase.findAllForSimpleDTO(pageable);
    }

    @Override
    public AdminResponseDriverDTO getDriverById(Long id){
        Driver driver = queryDriverUseCase.findById(id);

        return adminDriverMapper.toAdminResponseDriverDTO(driver);
    }

    @Override
    @Transactional
    public AdminResponseDriverDTO modify(Long id, ModifyDriverDTO dto){
        Driver driver = queryDriverUseCase.findById(id);
        DriverDocument document = queryCDSearchUseCase.findDriverDocumentById(id);

        updateDriverUseCase.modify(driver, adminDriverMapper.toModifyDriverCommand(dto));
        updateCDSearchUseCase.modify(document, driver);

        return adminDriverMapper.toAdminResponseDriverDTO(driver);
    }

    @Override
    public Pageable switchCondition(int page, int size, String condition) {
        switch (condition){
            case "nameASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
            case "nameDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "name"));
            case "racingClass" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "racingClass"));
            default:
                throw new CDSearchException(CDSearchExceptionType.CONDITION_ERROR_CD);
        }
    }
}
