package kr.co.F1FS.app.domain.constructor.application.port.in.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.ModifyConstructorCommand;

public interface UpdateConstructorUseCase {
    void modify(Constructor constructor, ModifyConstructorCommand command);
    void increaseFollower(Constructor constructor);
    void decreaseFollower(Constructor constructor);
}
