package kr.co.F1FS.app.domain.post.application.mapper.posting;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    public Post toPost(String title, User author){
        return Post.builder()
                .title(title)
                .author(author)
                .build();
    }

    public ResponsePostDTO toResponsePostDTO(Post post, List<String> tags){
        return ResponsePostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .blocks(post.getBlocks().stream()
                        .map(block -> new ResponsePostDTO.BlockContent(block.getContent(), block.getBlockType()))
                        .toList())
                .author(post.getAuthor().getNickname())
                .tags(tags)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .likeNum(post.getLikeNum())
                .build();
    }
}
