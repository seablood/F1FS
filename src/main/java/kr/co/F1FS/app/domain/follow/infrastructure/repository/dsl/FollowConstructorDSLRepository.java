package kr.co.F1FS.app.domain.follow.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;

import java.util.List;

public interface FollowConstructorDSLRepository {
    FollowConstructor findByUserAndConstructor(Long userId, Long constructorId);
    List<FollowConstructor> findAllByUser(Long userId);
    boolean existsByUserAndConstructor(Long userId, Long constructorId);
}
