package kr.co.F1FS.app.domain.elastic.application.port.in.user;

import kr.co.F1FS.app.domain.elastic.domain.UserDocument;

public interface QueryUserSearchUseCase {
    UserDocument findById(Long id);
}
