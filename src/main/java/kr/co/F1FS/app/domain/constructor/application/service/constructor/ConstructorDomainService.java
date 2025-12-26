package kr.co.F1FS.app.domain.constructor.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.application.mapper.constructor.ConstructorMapper;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.CreateConstructorDTO;
import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.ModifyConstructorCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstructorDomainService {
    private final ConstructorMapper constructorMapper;

    public Constructor createEntity(CreateConstructorDTO dto){
        return constructorMapper.toConstructor(dto);
    }

    public void modify(Constructor constructor, ModifyConstructorCommand command){
        constructor.modify(command);
    }

    public void increaseFollower(Constructor constructor){
        constructor.increaseFollower();
    }

    public void decreaseFollower(Constructor constructor){
        constructor.decreaseFollower();
    }
}
