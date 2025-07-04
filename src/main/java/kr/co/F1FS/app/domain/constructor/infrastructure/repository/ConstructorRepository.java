package kr.co.F1FS.app.domain.constructor.infrastructure.repository;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructorRepository extends JpaRepository<Constructor, Long> {
    Optional<Constructor> findByName(String name);
    Page<Constructor> findAll(Pageable pageable);
}
