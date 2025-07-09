package kr.co.F1FS.app.domain.constructor.infrastructure.adapter;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.ConstructorRepository;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowConstructorPort;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowConstructorAdapter implements FollowConstructorPort {
    private final ConstructorRepository constructorRepository;

    @Override
    public Constructor findByIdNotDTO(Long id) {
        return constructorRepository.findById(id)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_NOT_FOUND));
    }

    @Override
    public void saveAndFlush(Constructor constructor) {
        constructorRepository.saveAndFlush(constructor);
    }
}
