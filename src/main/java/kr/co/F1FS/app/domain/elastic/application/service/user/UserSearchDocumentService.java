package kr.co.F1FS.app.domain.elastic.application.service.user;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.domain.UserDocument;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchDocumentService {
    private final DocumentMapper documentMapper;

    public UserDocument createDocument(User user){
        return documentMapper.toUserDocument(user);
    }

    public void modify(UserDocument userDocument, User user){
        userDocument.modify(user);
    }

    public void increaseFollowerNum(UserDocument userDocument){
        userDocument.increaseFollowerNum();
    }

    public void decreaseFollowerNum(UserDocument userDocument){
        userDocument.decreaseFollowerNum();
    }
}
