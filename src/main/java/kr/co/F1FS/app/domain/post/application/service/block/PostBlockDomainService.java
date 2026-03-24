package kr.co.F1FS.app.domain.post.application.service.block;

import kr.co.F1FS.app.domain.post.application.mapper.block.PostBlockMapper;
import kr.co.F1FS.app.domain.post.domain.PostBlock;
import kr.co.F1FS.app.domain.post.presentation.dto.ModifyPostBlockRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostBlockDomainService {
    private final PostBlockMapper postBlockMapper;

    public List<PostBlock> createPostBlockList(List<ModifyPostBlockRequestDTO.BlockRequest> list){
        List<PostBlock> blockList = new ArrayList<>();

        for (ModifyPostBlockRequestDTO.BlockRequest blockRequest : list){
            PostBlock block = postBlockMapper.toPostBlock(blockRequest.getContent(), blockRequest.getType());

            blockList.add(block);
        }

        return blockList;
    }
}
