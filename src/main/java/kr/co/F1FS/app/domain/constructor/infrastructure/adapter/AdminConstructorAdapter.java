package kr.co.F1FS.app.domain.constructor.infrastructure.adapter;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.admin.constructor.application.port.out.AdminConstructorPort;
import kr.co.F1FS.app.domain.constructor.application.service.ConstructorService;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.ConstructorRepository;
import kr.co.F1FS.app.domain.constructor.presentation.dto.CreateConstructorDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminConstructorAdapter implements AdminConstructorPort {
    private final ConstructorRepository constructorRepository;

    @Override
    public Constructor save(Constructor constructor) {
        return constructorRepository.save(constructor);
    }
}
