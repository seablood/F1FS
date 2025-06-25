package kr.co.F1FS.app.domain.post.application.mapper;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.post.presentation.dto.CreatePostDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import kr.co.F1FS.app.global.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {
    public Post toPost(CreatePostDTO dto, User author){
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(author)
                .build();
    }

    public PostLikeRelation toPostLikeRelation(User user, Post post){
        return PostLikeRelation.builder()
                .user(user)
                .post(post)
                .build();
    }

    public ResponsePostDTO toResponsePostDTO(Post post){
        return ResponsePostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getNickname())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .likeNum(post.getLikeNum())
                .build();
    }

    public ResponseSimplePostDTO toResponseSimplePostDTO(Post post){
        LocalDateTime postTime = TimeUtil.convertToKoreanTime(post.getCreatedAt());

        return ResponseSimplePostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor().getNickname())
                .createdAt(TimeUtil.formatPostTime(postTime))
                .likeNum(post.getLikeNum())
                .build();
    }
}
