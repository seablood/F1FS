package kr.co.F1FS.app.domain.postRoom.application.service;

import kr.co.F1FS.app.domain.elastic.application.port.in.post.DeletePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.post.QueryPostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.post.application.port.in.posting.DeletePostUseCase;
import kr.co.F1FS.app.domain.post.application.port.in.posting.QueryPostUseCase;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.postRoom.application.port.in.DeletePostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.application.port.in.QueryPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.application.port.out.PostRoomJpaPort;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeletePostRoomService implements DeletePostRoomUseCase {
    private final PostRoomJpaPort postRoomJpaPort;
    private final QueryPostRoomUseCase queryPostRoomUseCase;
    private final DeletePostUseCase deletePostUseCase;
    private final QueryPostUseCase queryPostUseCase;
    private final DeletePostSearchUseCase deletePostSearchUseCase;
    private final QueryPostSearchUseCase queryPostSearchUseCase;

    private static final BlockingQueue<Long> QUEUE = new LinkedBlockingQueue<>();

    @Async
    @Override
    public void addPostRoomId(Long roomId){
        QUEUE.add(roomId);
    }

    @Async
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    @Transactional
    public void deletePost(){
        log.info("삭제된 게시판의 게시글 삭제 진행...");
        List<Long> idList = QUEUE.stream().toList();

        for (Long roomId : idList){
            PostRoom postRoom = queryPostRoomUseCase.findByIdWithJoin(roomId);
            List<Post> posts = queryPostUseCase.findAllByPostRoom(postRoom);

            posts.stream().forEach(post -> {
                PostDocument document = queryPostSearchUseCase.findById(postRoom.getId());
                deletePostUseCase.delete(post);
                deletePostSearchUseCase.delete(document);
            });
        }
    }

    @Override
    public void delete(PostRoom postRoom) {
        postRoomJpaPort.delete(postRoom);
    }
}
