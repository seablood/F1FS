package kr.co.F1FS.app.domain.driver.application.port.in.driver;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.driver.ModifyDriverCommand;

public interface UpdateDriverUseCase {
    void modify(Driver driver, ModifyDriverCommand command);
    void transfer(Driver driver, Constructor constructor);
    void increaseFollower(Driver driver);
    void decreaseFollower(Driver driver);
}
