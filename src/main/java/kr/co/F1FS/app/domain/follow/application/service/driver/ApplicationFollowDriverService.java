package kr.co.F1FS.app.domain.follow.application.service.driver;

import kr.co.F1FS.app.domain.driver.application.port.in.driver.QueryDriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.UpdateDriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.application.port.in.driver.*;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.presentation.dto.driver.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationFollowDriverService implements FollowDriverUseCase {
    private final CreateFollowDriverUseCase createFollowDriverUseCase;
    private final DeleteFollowDriverUseCase deleteFollowDriverUseCase;
    private final QueryFollowDriverUseCase queryFollowDriverUseCase;
    private final CheckFollowDriverUseCase checkFollowDriverUseCase;
    private final SaveFollowDriverListUseCase saveFollowDriverListUseCase;
    private final UpdateDriverUseCase updateDriverUseCase;
    private final QueryDriverUseCase queryDriverUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    @Transactional
    public void toggle(User user, Long id){
        Driver driver = queryDriverUseCase.findById(id);
        cacheEvictUtil.evictCachingDriver(driver);

        if(checkFollowDriverUseCase.existsByUserAndDriver(user.getId(), driver.getId())){
            FollowDriver followDriver = queryFollowDriverUseCase.findByUserAndDriver(user.getId(), driver.getId());
            deleteFollowDriverUseCase.delete(followDriver);
            updateDriverUseCase.decreaseFollower(driver);
            return;
        }

        createFollowDriverUseCase.save(user, driver);
        updateDriverUseCase.increaseFollower(driver);
    }

    @Override
    @Transactional
    public List<ResponseFollowDriverDTO> getFollowingDriverByUser(User user){
        Long userId = user.getId();

        if(saveFollowDriverListUseCase.hasKey(userId)) return saveFollowDriverListUseCase.getFollowingList(userId);

        List<ResponseFollowDriverDTO> list = queryFollowDriverUseCase.findAllByUserForDTO(userId);
        if(list.isEmpty()) return list;

        saveFollowDriverListUseCase.saveFollowingList(userId, list);

        return list;
    }

    @Override
    @Transactional
    public List<ResponseFollowDriverDTO> getFollowingDriverByNickname(String nickname) {
        User user = queryUserUseCase.findByNickname(nickname);
        Long userId = user.getId();

        if(saveFollowDriverListUseCase.hasKey(userId)) return saveFollowDriverListUseCase.getFollowingList(userId);

        List<ResponseFollowDriverDTO> list = queryFollowDriverUseCase.findAllByUserForDTO(userId);
        if(list.isEmpty()) return list;

        saveFollowDriverListUseCase.saveFollowingList(userId, list);

        return list;
    }
}
