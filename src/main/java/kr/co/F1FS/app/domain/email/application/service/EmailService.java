package kr.co.F1FS.app.domain.email.application.service;

import kr.co.F1FS.app.domain.auth.application.port.in.verification.CreateVerificationUseCase;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.auth.presentation.dto.AuthorizationUserDTO;
import kr.co.F1FS.app.domain.email.application.mapper.EmailMapper;
import kr.co.F1FS.app.domain.email.application.port.in.EmailUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
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
    private final CreateVerificationUseCase createVerificationUseCase;
    private final QueryUserUseCase queryUserUseCase;

    // 계정 생성 인증 메일
    @Override
    public void sendCreateAccountEmail(String email, String nickname){
        VerificationCode verificationCode = createVerificationUseCase.createCode(email);
        String code = verificationCode.getCode();

        EmailProperties.Template template = emailProperties.getTemplate(EmailType.CREATE_ACCOUNT);

        String subject = template.getSubject();
        String content = template.getContent().replace("{{code}}", code);

        EmailDTO emailDTO = emailMapper.toEmailDTO(email, subject, content, "create_account");

        sendEmail(emailDTO, nickname);
    }

    // 비밀번호 변경 인증 메일
    @Override
    public void sendUpdatePasswordEmail(User user){
        VerificationCode verificationCode = createVerificationUseCase.createCode(user.getEmail());
        String code = verificationCode.getCode();

        EmailProperties.Template template = emailProperties.getTemplate(EmailType.UPDATE_PASSWORD);

        String subject = template.getSubject();
        String content = template.getContent().replace("{{code}}", code);

        EmailDTO emailDTO = emailMapper.toEmailDTO(user.getEmail(), subject, content, "update_password");

        sendEmail(emailDTO, user.getNickname());
    }

    // 계정 활성화 인증 메일
    @Override
    public void sendActiveAccountEmail(AuthorizationUserDTO dto){
        User user = queryUserUseCase.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        String email = user.getEmail();
        String nickname = user.getNickname();

        VerificationCode verificationCode = createVerificationUseCase.createCode(email);
        String code = verificationCode.getCode();

        EmailProperties.Template template = emailProperties.getTemplate(EmailType.ACTIVE_ACCOUNT);

        String subject = template.getSubject();
        String content = template.getContent().replace("{{code}}", code);

        EmailDTO emailDTO = emailMapper.toEmailDTO(email, subject, content, "active_account");

        sendEmail(emailDTO, nickname);
    }

    public void sendEmail(EmailDTO emailDTO, String nickname){
        try{
            String emailContext = getContext(emailDTO, nickname);
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
