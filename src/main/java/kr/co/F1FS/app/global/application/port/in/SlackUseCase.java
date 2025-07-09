package kr.co.F1FS.app.global.application.port.in;

import com.slack.api.model.Field;

import java.util.HashMap;

public interface SlackUseCase {
    void sendErrorMessage(String title, HashMap<String, String> data);
    void sendComplainMessage(String title, HashMap<String, String> data);
    Field generateSlackField(String title, String value);
}
