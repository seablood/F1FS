package kr.co.F1FS.app.domain.sessionresult.application.port.out;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;

public interface SessionResultConstructorPort {
    Constructor getConstructorByNameNotDTO(String name);
}
