package kr.co.F1FS.app.domain.team.application.port.in.admin;


public interface ConstructorDriverRelationUseCase {
    void transfer(Integer number, String constructorName);
    void delete(Long id, String option);
}
