package kr.co.F1FS.app.domain.elastic.application.port.in.tag;

import java.util.List;

public interface TagSearchUseCase {
    List<String> getAutoTagList(String keyword);
}
