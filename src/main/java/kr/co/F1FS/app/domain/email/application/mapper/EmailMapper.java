package kr.co.F1FS.app.domain.email.application.mapper;

import kr.co.F1FS.app.domain.email.presentation.dto.EmailDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.config.email.EmailType;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.exception.email.EmailException;
import kr.co.F1FS.app.global.util.exception.email.EmailExceptionType;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {
    public EmailDTO toEmailDTO(User user, String subject, String content, String option){
        switch (option){
            case "create_account" :
                return EmailDTO.builder()
                        .to(user.getEmail())
                        .subject(subject)
                        .content(content)
                        .emailType(EmailType.CREATE_ACCOUNT)
                        .build();
            case "update_password" :
                return EmailDTO.builder()
                        .to(user.getEmail())
                        .subject(subject)
                        .content(content)
                        .emailType(EmailType.UPDATE_PASSWORD)
                        .build();
            case "active_account" :
                return EmailDTO.builder()
                        .to(user.getEmail())
                        .subject(subject)
                        .content(content)
                        .emailType(EmailType.ACTIVE_ACCOUNT)
                        .build();
            default:
                throw new EmailException(EmailExceptionType.EMAIL_TYPE_ERROR);
        }
    }

    public EmailDTO toEmailDTO(ResponseUserDTO dto, String subject, String content){
        return EmailDTO.builder()
                .to(dto.getEmail())
                .subject(subject)
                .content(content)
                .emailType(EmailType.CREATE_ACCOUNT)
                .build();
    }
}
