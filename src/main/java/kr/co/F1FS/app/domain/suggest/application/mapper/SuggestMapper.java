package kr.co.F1FS.app.domain.suggest.application.mapper;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.presentation.dto.CreateSuggestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import kr.co.F1FS.app.global.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SuggestMapper {
    public Suggest toSuggest(User user, CreateSuggestDTO dto){
        return Suggest.builder()
                .fromUser(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    public ResponseSuggestDTO toResponseSuggestDTO(Suggest suggest){
        LocalDateTime suggestTime = TimeUtil.convertToKoreanTime(suggest.getCreatedAt());

        return ResponseSuggestDTO.builder()
                .id(suggest.getId())
                .author(suggest.getFromUser().getNickname())
                .title(suggest.getTitle())
                .content(suggest.getContent())
                .createdAt(TimeUtil.formatPostTime(suggestTime))
                .isConfirmed(suggest.isConfirmed())
                .build();
    }
}
