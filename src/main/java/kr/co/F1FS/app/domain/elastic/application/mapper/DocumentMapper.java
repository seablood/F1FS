package kr.co.F1FS.app.domain.elastic.application.mapper;

import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.elastic.presentation.dto.CDSearchSuggestionDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDocumentDTO;
import kr.co.F1FS.app.global.util.TimeUtil;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {
    public CDSearchSuggestionDTO toCDSearchSuggestionDTO(DriverDocument driverDocument){
        return CDSearchSuggestionDTO.builder()
                .id(driverDocument.getId())
                .korName(driverDocument.getKorName())
                .engName(driverDocument.getEngName())
                .racingClass(driverDocument.getRacingClass())
                .type("driver")
                .build();
    }

    public CDSearchSuggestionDTO toCDSearchSuggestionDTO(ConstructorDocument constructorDocument){
        return CDSearchSuggestionDTO.builder()
                .id(constructorDocument.getId())
                .korName(constructorDocument.getKorName())
                .engName(constructorDocument.getEngName())
                .racingClass(constructorDocument.getRacingClass())
                .type("constructor")
                .build();
    }

    public ResponsePostDocumentDTO toResponsePostDocumentDTO(PostDocument postDocument){
        return ResponsePostDocumentDTO.builder()
                .id(postDocument.getId())
                .title(postDocument.getTitle())
                .author(postDocument.getAuthor())
                .createdAt(TimeUtil.formatPostTime(postDocument.getCreatedAt()))
                .likeNum(postDocument.getLikeNum())
                .build();
    }
}
