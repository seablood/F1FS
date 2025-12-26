package kr.co.F1FS.app.domain.constructor.application.service.admin;

import kr.co.F1FS.app.domain.constructor.application.mapper.admin.AdminConstructorMapper;
import kr.co.F1FS.app.domain.constructor.application.port.in.admin.AdminConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.presentation.dto.admin.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.constructor.presentation.dto.admin.CombinedConstructorRequest;
import kr.co.F1FS.app.domain.constructor.presentation.dto.admin.ModifyConstructorDTO;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.CreateConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.QueryConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.UpdateConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.CreateCDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.QueryCDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.UpdateCDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchException;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminConstructorService implements AdminConstructorUseCase {
    private final CreateConstructorUseCase createConstructorUseCase;
    private final UpdateConstructorUseCase updateConstructorUseCase;
    private final QueryConstructorUseCase queryConstructorUseCase;
    private final CreateCDSearchUseCase createCDSearchUseCase;
    private final UpdateCDSearchUseCase updateCDSearchUseCase;
    private final QueryCDSearchUseCase queryCDSearchUseCase;
    private final AdminConstructorMapper adminConstructorMapper;

    @Override
    @Transactional
    public AdminResponseConstructorDTO save(CombinedConstructorRequest request) {
        Constructor constructor = createConstructorUseCase.save(request.getConstructorDTO(), request.getCurrentSeasonDTO(),
                request.getSinceDebutDTO());
        createCDSearchUseCase.save(constructor);
        return adminConstructorMapper.toAdminResponseConstructorDTO(constructor);
    }

    @Override
    public Page<SimpleResponseConstructorDTO> findAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return queryConstructorUseCase.findAllForSimpleDTO(pageable);
    }

    @Override
    @Cacheable(value = "ConstructorAdminDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public AdminResponseConstructorDTO getConstructorById(Long id){
        Constructor constructor = queryConstructorUseCase.findById(id);

        return adminConstructorMapper.toAdminResponseConstructorDTO(constructor);
    }

    @Override
    @Transactional
    public AdminResponseConstructorDTO modify(Long id, ModifyConstructorDTO dto){
        Constructor constructor = queryConstructorUseCase.findById(id);
        ConstructorDocument document = queryCDSearchUseCase.findConstructorDocumentById(id);

        updateConstructorUseCase.modify(constructor, adminConstructorMapper.toModifyConstructorCommand(dto));
        updateCDSearchUseCase.modify(document, constructor);

        return adminConstructorMapper.toAdminResponseConstructorDTO(constructor);
    }

    @Override
    public Pageable switchCondition(int page, int size, String condition) {
        switch (condition){
            case "nameASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "korName"));
            case "nameDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "korName"));
            case "racingClass" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "racingClass"));
            default:
                throw new CDSearchException(CDSearchExceptionType.CONDITION_ERROR_CD);
        }
    }
}
