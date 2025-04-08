package kr.co.F1FS.app.config.email;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class EmailProperties {
    @Value("${email.templates.create_account.subject}")
    private String createAccountSubject;
    @Value("${email.templates.create_account.content}")
    private String createAccountContent;
    @Value("${email.templates.update_password.subject}")
    private String updatePasswordSubject;
    @Value("${email.templates.update_password.content}")
    private String updatePasswordContent;

    private Map<String, Template> templateMap = new HashMap<>();

    @PostConstruct
    public void init() {
        templateMap.put("create_account", new Template(createAccountSubject, createAccountContent));
        templateMap.put("update_password", new Template(updatePasswordSubject, updatePasswordContent));
    }

    public Template getTemplate(EmailType emailType){
        if(templateMap.containsKey(emailType.getKey())){
            return templateMap.get(emailType.getKey());
        } else {
            throw new IllegalArgumentException("템플릿을 찾을 수 없습니다.");
        }
    }

    @Data
    @AllArgsConstructor
    public static class Template {
        private String subject;
        private String content;
    }
}
