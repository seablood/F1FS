package kr.co.F1FS.app.domain.elastic.application.port.in.suggest;

import java.util.List;

public interface SuggestKeywordSearchUseCase {
    List<String> getAutoKeywordList(String keyword);
}
