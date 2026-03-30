package kr.co.F1FS.app.domain.elastic.application.port.in.user;

import kr.co.F1FS.app.domain.elastic.domain.UserDocument;
import kr.co.F1FS.app.domain.user.domain.User;

public interface UpdateUserSearchUseCase {
    void modify(UserDocument userDocument, User user);
    void increaseFollowerNum(UserDocument userDocument);
    void decreaseFollowerNum(UserDocument userDocument);
}
