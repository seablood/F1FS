package kr.co.F1FS.app.domain.auth.infrastructure.repository;

import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findVerificationCodeByEmailAndCode(String email, String code);
}
