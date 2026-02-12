package kr.co.F1FS.app.domain.grandprix.application.port.in;

public interface CheckGrandPrixUseCase {
    boolean existsGrandPrixByNameAndEngName(String name, String engName);
}
