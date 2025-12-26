package kr.co.F1FS.app.domain.elastic.application.mapper;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.elastic.domain.*;
import kr.co.F1FS.app.domain.elastic.presentation.dto.CDSearchSuggestionDTO;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDocumentDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixSearchDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDocumentDTO;
import kr.co.F1FS.app.global.util.TimeUtil;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {
    public ConstructorDocument toConstructorDocument(Constructor constructor){
        return ConstructorDocument.builder().constructor(constructor).build();
    }

    public DriverDocument toDriverDocument(Driver driver){
        return DriverDocument.builder().driver(driver).build();
    }

    public GrandPrixDocument toGrandPrixDocument(GrandPrix grandPrix){
        return GrandPrixDocument.builder().grandPrix(grandPrix).build();
    }

    public PostDocument toPostDocument(Post post){
        return PostDocument.builder().post(post).build();
    }

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

    public ResponseGrandPrixSearchDTO toResponseGrandPrixSearchDTO(GrandPrixDocument grandPrixDocument){
        return ResponseGrandPrixSearchDTO.builder()
                .id(grandPrixDocument.getId())
                .korName(grandPrixDocument.getKorName())
                .engName(grandPrixDocument.getEngName())
                .season(grandPrixDocument.getSeason())
                .build();
    }

    public ResponseChatRoomDocumentDTO toResponseChatRoomDocumentDTO(ChatRoomDocument chatRoomDocument){
        return ResponseChatRoomDocumentDTO.builder()
                .id(chatRoomDocument.getId())
                .name(chatRoomDocument.getName())
                .description(chatRoomDocument.getDescription())
                .memberCount(chatRoomDocument.getMemberCount())
                .build();
    }
}
