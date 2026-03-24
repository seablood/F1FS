package kr.co.F1FS.app.domain.follow.application.port.out.constructor;

import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;

import java.util.List;

public interface FollowConstructorJpaPort {
    FollowConstructor save(FollowConstructor followConstructor);
    FollowConstructor findByUserAndConstructor(Long userId, Long constructorId);
    List<FollowConstructor> findAllByUser(Long userId);
    boolean existsByUserAndConstructor(Long userId, Long constructorId);
    void delete(FollowConstructor followConstructor);
}
