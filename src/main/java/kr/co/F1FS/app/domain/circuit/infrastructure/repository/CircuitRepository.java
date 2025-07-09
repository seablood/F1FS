package kr.co.F1FS.app.domain.circuit.infrastructure.repository;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircuitRepository extends JpaRepository<Circuit, Long> {
    Page<Circuit> findAll(Pageable pageable);
}
