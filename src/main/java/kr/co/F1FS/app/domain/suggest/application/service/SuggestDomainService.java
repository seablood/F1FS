package kr.co.F1FS.app.domain.suggest.application.service;

import kr.co.F1FS.app.domain.suggest.application.mapper.SuggestMapper;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.presentation.dto.CreateSuggestDTO;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ModifySuggestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuggestDomainService {
    private final SuggestMapper suggestMapper;

    public Suggest createEntity(User user, CreateSuggestDTO dto){
        return suggestMapper.toSuggest(user, dto);
    }

    public void updateConfirmed(Suggest suggest){
        if(!suggest.isConfirmed()) suggest.updateConfirmed(true);
        else suggest.updateConfirmed(false);
    }

    public void modify(Suggest suggest, ModifySuggestDTO dto){
        suggest.modify(dto);
    }

    public boolean certification(User user, Suggest suggest){
        return AuthorCertification.certification(user.getUsername(), suggest.getFromUser().getUsername());
    }
}
