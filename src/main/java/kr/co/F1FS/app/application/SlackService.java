package kr.co.F1FS.app.application;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import kr.co.F1FS.app.global.util.Color;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Service
public class SlackService {
    @Value("${webhook.slack.url}")
    private String SLACK_WEBHOOK_URL;
    private final Slack slackClient = Slack.getInstance();

    public void sendErrorMessage(String title, HashMap<String, String> data){
        try{
            slackClient.send(SLACK_WEBHOOK_URL, payload(p -> p
                    .text(title)
                    .attachments(List.of(
                            Attachment.builder().color(Color.RED.getCode())
                                    .fields(
                                            data.keySet().stream().map(key -> generateSlackField(key, data.get(key)))
                                                    .toList()
                                    ).build()
                    ))));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Field generateSlackField(String title, String value){
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }
}
