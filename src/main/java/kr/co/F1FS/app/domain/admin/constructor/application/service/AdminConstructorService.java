package kr.co.F1FS.app.domain.admin.constructor.application.service;

import kr.co.F1FS.app.domain.admin.constructor.application.mapper.AdminConstructorMapper;
import kr.co.F1FS.app.domain.admin.constructor.application.port.in.AdminConstructorUseCase;
import kr.co.F1FS.app.domain.admin.constructor.application.port.out.AdminConstructorCDSearchPort;
import kr.co.F1FS.app.domain.admin.constructor.application.port.out.AdminConstructorPort;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.CombinedConstructorRequest;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.ModifyConstructorDTO;
import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.elastic.application.port.in.CDSearchUseCase;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminConstructorService implements AdminConstructorUseCase {
    private final ConstructorUseCase constructorUseCase;
    private final CDSearchUseCase searchUseCase;
    private final AdminConstructorPort constructorPort;
    private final AdminConstructorCDSearchPort searchPort;
    private final AdminConstructorMapper adminConstructorMapper;

    @Override
    @Transactional
    public AdminResponseConstructorDTO save(CombinedConstructorRequest request) {
        Constructor constructor = constructorPort.save(constructorUseCase.save(request.getConstructorDTO(), request.getCurrentSeasonDTO(),
                request.getSinceDebutDTO()));
        searchPort.save(searchUseCase.save(constructor));
        return adminConstructorMapper.toAdminResponseConstructorDTO(constructor);
    }

    @Override
    public Page<SimpleResponseConstructorDTO> findAll(int page, int size, String condition){
        return constructorUseCase.findAll(page, size, condition);
    }

    @Override
    public AdminResponseConstructorDTO getConstructorById(Long id){
        Constructor constructor = constructorPort.findById(id);

        return adminConstructorMapper.toAdminResponseConstructorDTO(constructor);
    }

    @Override
    @Transactional
    public AdminResponseConstructorDTO modify(Long id, ModifyConstructorDTO dto){
        Constructor constructor = constructorPort.findById(id);
        constructorUseCase.modify(constructor, adminConstructorMapper.toModifyConstructorCommand(dto));
        constructorPort.saveAndFlush(constructor);

        return adminConstructorMapper.toAdminResponseConstructorDTO(constructor);
    }
}
