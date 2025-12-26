package kr.co.F1FS.app.domain.constructor.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.UpdateConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.constructor.ConstructorJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.ModifyConstructorCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateConstructorService implements UpdateConstructorUseCase {
    private final ConstructorJpaPort constructorJpaPort;
    private final ConstructorDomainService constructorDomainService;

    @Override
    public void modify(Constructor constructor, ModifyConstructorCommand command) {
        constructorDomainService.modify(constructor, command);

        constructorJpaPort.save(constructor);
    }

    @Override
    public void increaseFollower(Constructor constructor) {
        constructorDomainService.increaseFollower(constructor);

        constructorJpaPort.saveAndFlush(constructor);
    }

    @Override
    public void decreaseFollower(Constructor constructor) {
        constructorDomainService.decreaseFollower(constructor);

        constructorJpaPort.saveAndFlush(constructor);
    }
}
