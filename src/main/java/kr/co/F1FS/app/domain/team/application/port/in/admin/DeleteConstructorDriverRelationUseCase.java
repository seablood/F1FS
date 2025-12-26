package kr.co.F1FS.app.domain.team.application.port.in.admin;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;

public interface DeleteConstructorDriverRelationUseCase {
    void delete(Driver driver, String option);
}
