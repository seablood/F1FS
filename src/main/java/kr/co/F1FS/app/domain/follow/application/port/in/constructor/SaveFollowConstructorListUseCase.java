package kr.co.F1FS.app.domain.follow.application.port.in.constructor;

import kr.co.F1FS.app.domain.follow.presentation.dto.constructor.ResponseFollowConstructorDTO;

import java.util.List;

public interface SaveFollowConstructorListUseCase {
    boolean hasKey(Long keyword);
    List<ResponseFollowConstructorDTO> getFollowingList(Long keyword);
    void saveFollowingList(Long keyword, List<ResponseFollowConstructorDTO> list);
}
