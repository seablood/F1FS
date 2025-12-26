package kr.co.F1FS.app.domain.auth.application.service.verification;

import kr.co.F1FS.app.domain.auth.application.port.in.verification.VerifyAndDeleteVerificationUseCase;
import kr.co.F1FS.app.domain.auth.application.port.out.AuthJpaPort;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.UpdateUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.global.util.exception.verify.VerifyException;
import kr.co.F1FS.app.global.util.exception.verify.VerifyExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerifyAndDeleteVerificationService implements VerifyAndDeleteVerificationUseCase {
    private final AuthJpaPort authJpaPort;
    private final QueryUserUseCase queryUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @Override
    @Transactional
    public void verifyCode(String email, String code) {
        VerificationCode verificationCode = authJpaPort.findVerificationCodeByEmailAndCode(email, code);

        if(verificationCode.isExpired()){
            throw new VerifyException(VerifyExceptionType.VERIFICATION_ERROR);
        } else {
            User user = queryUserUseCase.findUserByEmail(verificationCode.getEmail());
            updateUserUseCase.updateRole(user, Role.USER);
        }

        authJpaPort.delete(verificationCode);
        log.info("인증 완료 및 인증 코드 삭제 : {}", email);
    }

    @Override
    @Transactional
    public void delete(VerificationCode code) {
        authJpaPort.delete(code);
    }
}
