package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.CreatePostDTO;
import kr.co.F1FS.app.dto.ModifyPostDTO;
import kr.co.F1FS.app.dto.ResponsePostDTO;
import kr.co.F1FS.app.model.Post;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.PostRepository;
import kr.co.F1FS.app.util.AuthorCertification;
import kr.co.F1FS.app.util.ValidationService;
import kr.co.F1FS.app.util.post.PostException;
import kr.co.F1FS.app.util.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ValidationService validationService;

    @Transactional
    public Post save(CreatePostDTO dto, User author){
        Post post = CreatePostDTO.toEntity(dto, author);
        validationService.checkValid(post);
        return postRepository.save(post);
    }

    public List<ResponsePostDTO> findAll(){
        return postRepository.findAll().stream()
                .map(post -> ResponsePostDTO.toDto(post))
                .toList();
    }

    public ResponsePostDTO findById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        return ResponsePostDTO.toDto(post);
    }

    public Post findByIdNotDTO(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
    }

    @Transactional
    public ResponsePostDTO modify(Long id, ModifyPostDTO dto, User user){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            throw new PostException(PostExceptionType.NOT_AUTHORITY_UPDATE_POST);
        }
        post.modify(dto);
        postRepository.saveAndFlush(post);
        return ResponsePostDTO.toDto(post);
    }

    @Transactional
    public void delete(Long id, User user){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            throw new PostException(PostExceptionType.NOT_AUTHORITY_DELETE_POST);
        }
        postRepository.delete(post);
    }
}
