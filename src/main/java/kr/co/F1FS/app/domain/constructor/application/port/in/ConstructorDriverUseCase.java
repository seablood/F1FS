package kr.co.F1FS.app.domain.constructor.application.port.in;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;

public interface ConstructorDriverUseCase {
    Constructor findByNameNotDTONotCache(String name);
}
