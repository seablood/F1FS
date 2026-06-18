package kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm;

public interface CheckPostRoomDeleteFormUseCase {
    boolean existsByPostRoom(Long roomId);
}
