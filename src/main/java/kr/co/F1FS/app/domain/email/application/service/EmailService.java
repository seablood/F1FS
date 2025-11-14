package kr.co.F1FS.app.domain.email.application.service;

import kr.co.F1FS.app.domain.email.application.mapper.EmailMapper;
import kr.co.F1FS.app.domain.email.application.port.in.EmailUseCase;
import kr.co.F1FS.app.global.config.email.EmailProperties;
import kr.co.F1FS.app.global.config.email.EmailType;
import kr.co.F1FS.app.domain.email.presentation.dto.EmailDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.email.EmailException;
import kr.co.F1FS.app.global.util.exception.email.EmailExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService implements EmailUseCase {
    private final EmailMapper emailMapper;
    private final EmailProperties emailProperties;
    private final SpringTemplateEngine springTemplateEngine;
    private final EmailSendService emailSendService;

    @Override
    public void sendAuthEmail(User user, String code, String option){
        try {
            switch (option){
                case "create_account" :
                    sendCreateAccountEmail(user, code);
                    break;
                case "update_password" :
                    sendUpdatePasswordEmail(user, code);
                    break;
                case "active_account" :
                    sendActiveAccountEmail(user, code);
                    break;
                default:
                    throw new RuntimeException("이메일 형식 오류");
            }
        } catch (Exception e){
            throw new EmailException(EmailExceptionType.SEND_EMAIL_ERROR);
        }
    }

    // 계정 생성 인증 메일
    @Override
    public void sendCreateAccountEmail(User user, String code){
        EmailProperties.Template template = emailProperties.getTemplate(EmailType.CREATE_ACCOUNT);

        String subject = template.getSubject();
        String content = template.getContent().replace("{{code}}", code);

        EmailDTO emailDTO = emailMapper.toEmailDTO(user, subject, content, "create_account");

        sendEmail(emailDTO, user);
    }

    // 비밀번호 변경 인증 메일
    @Override
    public void sendUpdatePasswordEmail(User user, String code){
        EmailProperties.Template template = emailProperties.getTemplate(EmailType.UPDATE_PASSWORD);

        String subject = template.getSubject();
        String content = template.getContent().replace("{{code}}", code);

        EmailDTO emailDTO = emailMapper.toEmailDTO(user, subject, content, "update_password");

        sendEmail(emailDTO, user);
    }

    @Override
    public void sendActiveAccountEmail(User user, String code){
        EmailProperties.Template template = emailProperties.getTemplate(EmailType.ACTIVE_ACCOUNT);

        String subject = template.getSubject();
        String content = template.getContent().replace("{{code}}", code);

        EmailDTO emailDTO = emailMapper.toEmailDTO(user, subject, content, "active_account");

        sendEmail(emailDTO, user);
    }

    public void sendEmail(EmailDTO emailDTO, User user){
        try{
            String emailContext = getContext(emailDTO, user.getNickname());
            emailSendService.addEmail(emailDTO.getTo(), emailDTO.getSubject(), emailContext, emailDTO.getEmailType());
        } catch (Exception e){
            throw new EmailException(EmailExceptionType.SEND_EMAIL_ERROR);
        }
    }

    public String getContext(EmailDTO emailDTO, String nickname){
        Context context = new Context();
        context.setVariable("title", emailDTO.getSubject());
        context.setVariable("nickname", nickname);
        context.setVariable("content", emailDTO.getContent());

        String emailTemplate = emailDTO.getEmailType().getTemplateName();

        return springTemplateEngine.process(emailTemplate, context);
    }
}
