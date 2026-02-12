package kr.co.F1FS.app.domain.constructor.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.UpdateConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.constructor.ConstructorJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.ModifyConstructorCommand;
import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateConstructorService implements UpdateConstructorUseCase {
    private final ConstructorJpaPort constructorJpaPort;
    private final ConstructorDomainService constructorDomainService;
    private final SaveSuggestKeywordSearchRedisUseCase saveSuggestKeywordSearchRedisUseCase;

    @Override
    public void modify(Constructor constructor, ModifyConstructorCommand command) {
        constructorDomainService.modify(constructor, command);

        constructorJpaPort.save(constructor);
        saveSuggestKeyword(constructor);
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

    public void saveSuggestKeyword(Constructor constructor){
        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(constructor.getName());
        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(constructor.getEngName());
    }
}
