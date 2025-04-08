package kr.co.F1FS.app.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.co.F1FS.app.config.email.EmailQueueMemory;
import kr.co.F1FS.app.config.email.EmailType;
import kr.co.F1FS.app.util.email.EmailException;
import kr.co.F1FS.app.util.email.EmailExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailAsyncService {
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;
    private static final BlockingQueue<EmailQueueMemory> QUEUE = new LinkedBlockingQueue<>();
    private static final int MAX_SIZE = 10;

    @Async
    public void addEmail(String to, String subject, String content, EmailType emailType) throws MessagingException{
        QUEUE.add(new EmailQueueMemory(to, subject, content, emailType));
    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void pickSendingEmail(){
        try{
            List<EmailQueueMemory> list = new ArrayList<>();
            EmailQueueMemory memory;

            while (!QUEUE.isEmpty()){
                memory = QUEUE.poll();
                list.add(memory);

                if(list.size() >= MAX_SIZE){
                    sendEmailList(list);
                    list.clear();
                }
            }
            if(!list.isEmpty()){
                sendEmailList(list);
            }
        } catch (Exception e){
            throw new EmailException(EmailExceptionType.SEND_EMAIL_ERROR);
        }
    }

    public void sendSimpleMailMessage(){
        SimpleMailMessage message = new SimpleMailMessage();

        try{
            message.setTo("dudwnszero@naver.com");
            message.setSubject("테스트 이메일");
            message.setText("테스트 이메일");

            javaMailSender.send(message);
        } catch (Exception e){
            throw new EmailException(EmailExceptionType.SEND_EMAIL_ERROR);
        }
    }

    public void sendEmailList(List<EmailQueueMemory> list) throws MessagingException {
        MimeMessage[] messages = new MimeMessage[list.size()];
        for (int i = 0; i< list.size(); i++){
            EmailQueueMemory memory = list.get(i);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setFrom(from);
            messageHelper.setTo(memory.getTo());
            messageHelper.setSubject(memory.getSubject());
            messageHelper.setText(memory.getContent(), true);

            messages[i] = message;
            log.info(memory.getTo());
        }

        try{
            javaMailSender.send(messages);
        } catch (Exception e){
            throw new EmailException(EmailExceptionType.SEND_EMAIL_ERROR);
        }
    }
}
