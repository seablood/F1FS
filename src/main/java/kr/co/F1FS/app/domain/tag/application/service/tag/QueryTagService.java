package kr.co.F1FS.app.domain.tag.application.service.tag;

import kr.co.F1FS.app.domain.tag.application.mapper.tag.TagMapper;
import kr.co.F1FS.app.domain.tag.application.port.in.tag.QueryTagUseCase;
import kr.co.F1FS.app.domain.tag.application.port.out.tag.TagJpaPort;
import kr.co.F1FS.app.domain.tag.domain.Tag;
import kr.co.F1FS.app.global.presentation.dto.tag.ResponseTagDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryTagService implements QueryTagUseCase {
    private final TagJpaPort tagJpaPort;
    private final TagMapper tagMapper;

    @Override
    public Page<ResponseTagDTO> findAllForDTO(Pageable pageable) {
        return tagJpaPort.findAll(pageable).map(tag -> tagMapper.toResponseTagDTO(tag));
    }

    @Override
    public List<Tag> saveTagList(List<String> tags) {
        List<Tag> tagList = new ArrayList<>();
        for (String tagString : tags){
            Tag tag = tagJpaPort.findByNameOrNewDomain(tagString);
            tagList.add(tag);
        }

        return tagList;
    }
}
