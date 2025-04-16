package kr.co.F1FS.app.application.email;

import kr.co.F1FS.app.global.config.email.EmailProperties;
import kr.co.F1FS.app.global.config.email.EmailType;
import kr.co.F1FS.app.presentation.email.dto.EmailDTO;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.global.util.exception.email.EmailException;
import kr.co.F1FS.app.global.util.exception.email.EmailExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailProperties emailProperties;
    private final SpringTemplateEngine springTemplateEngine;
    private final EmailAsyncService emailAsyncService;

    // 계정 생성 인증 메일
    public void sendCreateAccountEmail(User user, String code){
        EmailProperties.Template template = emailProperties.getTemplate(EmailType.CREATE_ACCOUNT);

        String subject = template.getSubject();
        String content = template.getContent().replace("{{code}}", code);

        EmailDTO emailDTO = EmailDTO.builder()
                .to(user.getEmail())
                .subject(subject)
                .content(content)
                .emailType(EmailType.CREATE_ACCOUNT)
                .build();

        sendEmail(emailDTO, user);
    }

    // 비밀번호 변경 인증 메일
    public void sendUpdatePasswordEmail(User user, String code){
        EmailProperties.Template template = emailProperties.getTemplate(EmailType.UPDATE_PASSWORD);

        String subject = template.getSubject();
        String content = template.getContent().replace("{{code}}", code);

        EmailDTO emailDTO = EmailDTO.builder()
                .to(user.getEmail())
                .subject(subject)
                .content(content)
                .emailType(EmailType.CREATE_ACCOUNT)
                .build();

        sendEmail(emailDTO, user);
    }

    public void sendEmail(EmailDTO emailDTO, User user){
        try{
            String emailContext = getContext(emailDTO, user.getNickname());
            emailAsyncService.addEmail(emailDTO.getTo(), emailDTO.getSubject(), emailContext, emailDTO.getEmailType());
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
