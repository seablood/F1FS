package kr.co.F1FS.app.domain.driver.application.port.out;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;

public interface DriverConstructorPort {
    Constructor findByName(String team);
}
