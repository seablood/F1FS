package kr.co.F1FS.app.domain.elastic.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.UserDocument;

public interface UserSearchRepoPort {
    void save(UserDocument userDocument);
    UserDocument findById(Long id);
    void delete(UserDocument userDocument);
}
