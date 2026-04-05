package kr.co.F1FS.app.domain.elastic.application.service.bookmark;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.bookmark.QueryBookmarkSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.bookmark.UpdateBookmarkSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;
import kr.co.F1FS.app.domain.elastic.presentation.dto.ModifyBookmarkSearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModifyBookmarkSearchAsyncService {
    private final UpdateBookmarkSearchUseCase updateBookmarkSearchUseCase;
    private final QueryBookmarkSearchUseCase queryBookmarkSearchUseCase;
    private final DocumentMapper documentMapper;
    private static final BlockingQueue<ModifyBookmarkSearchDTO> QUEUE = new LinkedBlockingQueue<>();
    private static final int MAX = 20;

    @Async
    public void addDTO(Long postId, String title){
        QUEUE.add(documentMapper.toModifyBookmarkSearchDTO(postId, title));
    }

    @Async
    @Scheduled(fixedRate = 5000)
    public void pickModifyDTO(){
        List<ModifyBookmarkSearchDTO> list = new ArrayList<>();

        while (!QUEUE.isEmpty()){
            list.add(QUEUE.poll());

            if(list.size() >= MAX){
                modify(list);
                list.clear();
            }
        }
        if(!list.isEmpty()){
            modify(list);
        }
    }

    public void modify(List<ModifyBookmarkSearchDTO> list){
        list.stream().forEach(dto -> {
            List<BookmarkDocument> documentList = queryBookmarkSearchUseCase.findAllByPostId(dto.getPostId());
            documentList.stream().forEach(document -> updateBookmarkSearchUseCase.modify(document, dto.getTitle()));
        });
    }
}
