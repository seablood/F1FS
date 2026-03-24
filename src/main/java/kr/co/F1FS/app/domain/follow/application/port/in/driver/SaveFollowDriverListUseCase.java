package kr.co.F1FS.app.domain.follow.application.port.in.driver;

import kr.co.F1FS.app.domain.follow.presentation.dto.driver.ResponseFollowDriverDTO;

import java.util.List;

public interface SaveFollowDriverListUseCase {
    boolean hasKey(Long keyword);
    List<ResponseFollowDriverDTO> getFollowingList(Long keyword);
    void saveFollowingList(Long keyword, List<ResponseFollowDriverDTO> list);
}
