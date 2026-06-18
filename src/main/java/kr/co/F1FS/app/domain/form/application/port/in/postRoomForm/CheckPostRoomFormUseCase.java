package kr.co.F1FS.app.domain.form.application.port.in.postRoomForm;

public interface CheckPostRoomFormUseCase {
    boolean existsByUser(Long userId);
}
