package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.CreatePostDTO;
import kr.co.F1FS.app.dto.ModifyPostDTO;
import kr.co.F1FS.app.dto.ResponsePostDTO;
import kr.co.F1FS.app.model.Post;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.PostRepository;
import kr.co.F1FS.app.util.post.PostException;
import kr.co.F1FS.app.util.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post save(CreatePostDTO dto, User author){
        Post post = CreatePostDTO.toEntity(dto, author);
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
    public ResponsePostDTO modify(Long id, ModifyPostDTO dto){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        post.modify(dto);
        postRepository.saveAndFlush(post);
        return ResponsePostDTO.toDto(post);
    }
}
