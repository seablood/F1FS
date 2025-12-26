package kr.co.F1FS.app.domain.auth.application.service.verification;

import kr.co.F1FS.app.domain.auth.application.mapper.AuthMapper;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class VerificationDomainService {
    private final AuthMapper authMapper;

    public VerificationCode createEntity(User user, String code){
        VerificationCode verificationCode = authMapper.toVerificationCode(user, code);

        return verificationCode;
    }

    public VerificationCode createEntity(ResponseUserDTO dto, String code){
        VerificationCode verificationCode = authMapper.toVerificationCode(dto, code);

        return verificationCode;
    }

    public boolean isExpired(VerificationCode code){
        return code.isExpired();
    }

    public String generateVerificationCode() {
        String base56Character = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for(int i = 0; i<6; i++){
            int idx = random.nextInt(base56Character.length());
            sb.append(base56Character.charAt(idx));
        }

        return sb.toString();
    }
}
