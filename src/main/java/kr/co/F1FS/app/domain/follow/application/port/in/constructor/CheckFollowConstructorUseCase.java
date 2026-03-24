package kr.co.F1FS.app.domain.follow.application.port.in.constructor;

public interface CheckFollowConstructorUseCase {
    boolean existsByUserAndConstructor(Long userId, Long constructorId);
}
