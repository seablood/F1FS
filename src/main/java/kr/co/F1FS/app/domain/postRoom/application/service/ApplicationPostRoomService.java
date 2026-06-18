package kr.co.F1FS.app.domain.postRoom.application.service;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.domain.elastic.application.port.in.postRoom.CreatePostRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.postRoom.QueryPostRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.postRoom.UpdatePostRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;
import kr.co.F1FS.app.domain.form.application.port.in.postRoomForm.QueryPostRoomFormUseCase;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.postRoom.application.mapper.PostRoomMapper;
import kr.co.F1FS.app.domain.postRoom.application.port.in.*;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.*;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.global.presentation.dto.postRoom.ResponsePostRoomDTO;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomException;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ApplicationPostRoomService implements PostRoomUseCase {
    private final CreatePostRoomUseCase createPostRoomUseCase;
    private final UpdatePostRoomUseCase updatePostRoomUseCase;
    private final DeletePostRoomUseCase deletePostRoomUseCase;
    private final QueryPostRoomUseCase queryPostRoomUseCase;
    private final VerifyPostRoomUseCase verifyPostRoomUseCase;
    private final CreatePostRoomSearchUseCase createPostRoomSearchUseCase;
    private final UpdatePostRoomSearchUseCase updatePostRoomSearchUseCase;
    private final QueryPostRoomSearchUseCase queryPostRoomSearchUseCase;
    private final QueryPostRoomFormUseCase queryPostRoomFormUseCase;
    private final PostRoomMapper postRoomMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenService jwtTokenService;

    @Override
    @Transactional
    public ResponsePostRoomDTO save(CreatePostRoomDTO dto, User user, Long formId) {
        if(!dto.isPublic()){
            PostRoomForm postRoomForm = queryPostRoomFormUseCase.findByIdWithJoin(formId);
            dto.setPassword(postRoomForm.getPassword());
        }
        PostRoom postRoom = createPostRoomUseCase.save(dto, user);

        createPostRoomSearchUseCase.save(postRoom);

        return postRoomMapper.toResponsePostRoomDTO(postRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostRoomListDTO> getPostRoomList(int page, int size, String condition) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostRoomUseCase.findPostRoomListForDTO(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostRoomListDTO> getPostRoomListByUser(int page, int size, String condition, User user) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostRoomUseCase.findAllByUserForDTO(user.getId(), pageable);
    }

    @Override
    @Transactional
    public ResponsePostRoomDTO modifyInfo(ModifyPostRoomInfoDTO dto, Long postRoomId, User user) {
        PostRoom postRoom = queryPostRoomUseCase.findByIdWithJoin(postRoomId);
        PostRoomDocument document = queryPostRoomSearchUseCase.findById(postRoomId);

        updatePostRoomUseCase.modifyInfo(dto, postRoom, user);
        updatePostRoomSearchUseCase.modifyInfo(postRoom, document);

        return postRoomMapper.toResponsePostRoomDTO(postRoom);
    }

    @Override
    @Transactional
    public ResponsePostRoomDTO modifyIsPublic(ModifyPostRoomPublicDTO dto, Long postRoomId, User user) {
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        PostRoom postRoom = queryPostRoomUseCase.findByIdWithJoin(postRoomId);
        PostRoomDocument document = queryPostRoomSearchUseCase.findById(postRoomId);

        updatePostRoomUseCase.modifyIsPublic(dto, postRoom, user);
        updatePostRoomSearchUseCase.modifyIsPublic(postRoom, document);

        return postRoomMapper.toResponsePostRoomDTO(postRoom);
    }

    @Override
    public void verifyPrivatePostRoom(VerifyPostRoomDTO dto, Long postRoomId, User user, HttpServletResponse response) {
        PostRoom postRoom = queryPostRoomUseCase.findById(postRoomId);

        if(!verifyPostRoomUseCase.verify(dto, postRoom)){
            throw new PostRoomException(PostRoomExceptionType.NOT_AUTHORITY_PRIVATE_POST_ROOM);
        } else {
            jwtTokenService.sendPrivatePostToken(response, jwtTokenService.createToken(postRoom, user, Duration.ofMinutes(3)));
        }
    }

    @Override
    @Transactional
    public void delete(Long roomId) {
        PostRoom postRoom = queryPostRoomUseCase.findById(roomId);

        deletePostRoomUseCase.delete(postRoom);
        deletePostRoomUseCase.addPostRoomId(roomId);
    }

    public Pageable conditionSwitch(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            case "older" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default:
                throw new PostRoomException(PostRoomExceptionType.CONDITION_ERROR_POST_ROOM);
        }
    }
}
