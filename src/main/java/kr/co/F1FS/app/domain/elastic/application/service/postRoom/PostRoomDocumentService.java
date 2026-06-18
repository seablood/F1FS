package kr.co.F1FS.app.domain.elastic.application.service.postRoom;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostRoomDocumentService {
    private final DocumentMapper documentMapper;

    public PostRoomDocument createDocument(PostRoom postRoom){
        return documentMapper.toPostRoomDocument(postRoom);
    }

    public void modifyInfo(PostRoom postRoom, PostRoomDocument document){
        document.modifyInfo(postRoom);
    }

    public void modifyIsPublic(PostRoom postRoom, PostRoomDocument document){
        document.modifyIsPublic(postRoom);
    }
}
