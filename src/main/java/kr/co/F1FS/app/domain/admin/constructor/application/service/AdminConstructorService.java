package kr.co.F1FS.app.domain.admin.constructor.application.service;

import kr.co.F1FS.app.domain.admin.constructor.application.port.in.AdminConstructorUseCase;
import kr.co.F1FS.app.domain.admin.constructor.application.port.out.AdminConstructorCDSearchPort;
import kr.co.F1FS.app.domain.admin.constructor.application.port.out.AdminConstructorPort;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.CombinedConstructorRequest;
import kr.co.F1FS.app.domain.constructor.application.mapper.ConstructorMapper;
import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.elastic.application.port.in.CDSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminConstructorService implements AdminConstructorUseCase {
    private final ConstructorUseCase constructorUseCase;
    private final CDSearchUseCase searchUseCase;
    private final AdminConstructorPort constructorPort;
    private final AdminConstructorCDSearchPort searchPort;
    private final ConstructorMapper constructorMapper;

    @Override
    @Transactional
    public AdminResponseConstructorDTO save(CombinedConstructorRequest request) {
        Constructor constructor = constructorPort.save(constructorUseCase.save(request.getConstructorDTO(), request.getCurrentSeasonDTO(),
                request.getSinceDebutDTO()));
        searchPort.save(searchUseCase.save(constructor));
        return constructorMapper.toAdminResponseConstructorDTO(constructor);
    }
}
