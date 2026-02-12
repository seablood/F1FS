package kr.co.F1FS.app.domain.tag.infrastructure.repository;

import kr.co.F1FS.app.domain.tag.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findAll(Pageable pageable);
    Optional<Tag> findByName(String name);
}
