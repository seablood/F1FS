package kr.co.F1FS.app.domain.elastic.application.port.in.tag;

import kr.co.F1FS.app.domain.tag.domain.Tag;

public interface CreateTagSearchUseCase {
    void save(Tag tag);
}
