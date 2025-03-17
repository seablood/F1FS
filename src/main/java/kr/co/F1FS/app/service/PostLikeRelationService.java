package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.model.Post;
import kr.co.F1FS.app.model.PostLikeRelation;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.PostLikeRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeRelationService {
    private final PostLikeRelationRepository relationRepository;
    private final PostService postService;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "PostNotDTO", key = "#id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "PostDTO", key = "#id", cacheManager = "redisLongCacheManager")
    })
    public void toggle(User user, Long id){
        Post post = postService.findByIdNotDTO(id);

        if(isLiked(user, post)){
            PostLikeRelation relation = relationRepository.findPostLikeRelationByUserAndPost(user, post);
            relationRepository.delete(relation);
            post.decreaseLike();
        }
        else {
            PostLikeRelation relation = PostLikeRelation.builder()
                    .user(user)
                    .post(post)
                    .build();
            relationRepository.save(relation);
            post.increaseLike();
        }
    }

    public boolean isLiked(User user, Post post){
        return relationRepository.existsPostLikeRelationByUserAndPost(user, post);
    }
}
