package kr.co.F1FS.app.domain.file.infrastructure.repository;

import kr.co.F1FS.app.domain.file.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    Optional<UploadFile> findByUrl(String url);
}
