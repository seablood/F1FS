package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.CreatePostDTO;
import kr.co.F1FS.app.dto.ModifyPostDTO;
import kr.co.F1FS.app.dto.ResponsePostDTO;
import kr.co.F1FS.app.model.Post;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.PostRepository;
import kr.co.F1FS.app.util.AuthorCertification;
import kr.co.F1FS.app.util.CacheEvictUtil;
import kr.co.F1FS.app.util.ValidationService;
import kr.co.F1FS.app.util.post.PostException;
import kr.co.F1FS.app.util.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ValidationService validationService;
    private final CacheEvictUtil cacheEvictUtil;

    @Transactional
    public Post save(CreatePostDTO dto, User author){
        Post post = CreatePostDTO.toEntity(dto, author);
        validationService.checkValid(post);
        return postRepository.save(post);
    }

    public Page<ResponsePostDTO> findAll(int page, int size, String condition){
        Pageable pageable = conditionSwitch(page, size, condition);

        return postRepository.findAll(pageable).map(post -> ResponsePostDTO.toDto(post));
    }

    public Page<ResponsePostDTO> findByTitleOrContent(String search, int page, int size, String option, String condition){
        Pageable pageable = conditionSwitch(page, size, condition);
        return optionSwitch(search, option, pageable);
    }

    @Cacheable(value = "PostDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponsePostDTO findById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        return ResponsePostDTO.toDto(post);
    }

    @Cacheable(value = "PostNotDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public Post findByIdNotDTO(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
    }

    @Transactional
    public ResponsePostDTO modify(Long id, ModifyPostDTO dto, User user){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        cacheEvictUtil.evictCachingPost(post);

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
        cacheEvictUtil.evictCachingPost(post);

        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            throw new PostException(PostExceptionType.NOT_AUTHORITY_DELETE_POST);
        }
        postRepository.delete(post);
    }

    public Pageable conditionSwitch(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            case "older" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            case "like" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "likeNum"));
            default:
                throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
        }
    }

    public Page<ResponsePostDTO> optionSwitch(String search, String option, Pageable pageable){
        switch (option){
            case "title" :
                return postRepository.findAllByTitleContainsIgnoreCase(search, pageable)
                        .map(post -> ResponsePostDTO.toDto(post));
            case "content" :
                return postRepository.findAllByContentContainsIgnoreCase(search, pageable)
                        .map(post -> ResponsePostDTO.toDto(post));
            case "titleOrContent" :
                return postRepository.findAllByTitleContainsIgnoreCaseOrContentContainsIgnoreCase(search, search, pageable)
                        .map(post -> ResponsePostDTO.toDto(post));
            case "author" :
                return postRepository.findByAuthorNicknameContainsIgnoreCase(search, pageable)
                        .map(post -> ResponsePostDTO.toDto(post));
            default:
                throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
        }
    }
}
