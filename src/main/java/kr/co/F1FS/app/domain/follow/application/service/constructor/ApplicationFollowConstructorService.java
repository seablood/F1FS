package kr.co.F1FS.app.domain.follow.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.QueryConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.UpdateConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.application.port.in.constructor.*;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.presentation.dto.constructor.ResponseFollowConstructorDTO;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationFollowConstructorService implements FollowConstructorUseCase {
    private final CreateFollowConstructorUseCase createFollowConstructorUseCase;
    private final DeleteFollowConstructorUseCase deleteFollowConstructorUseCase;
    private final QueryFollowConstructorUseCase queryFollowConstructorUseCase;
    private final CheckFollowConstructorUseCase checkFollowConstructorUseCase;
    private final SaveFollowConstructorListUseCase saveFollowConstructorListUseCase;
    private final UpdateConstructorUseCase updateConstructorUseCase;
    private final QueryConstructorUseCase queryConstructorUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    @Transactional
    public void toggle(User user, Long id){
        Constructor constructor = queryConstructorUseCase.findById(id);
        cacheEvictUtil.evictCachingConstructor(constructor);

        if(checkFollowConstructorUseCase.existsByUserAndConstructor(user.getId(), constructor.getId())){
            FollowConstructor followConstructor = queryFollowConstructorUseCase.findByUserAndConstructor(
                    user.getId(), constructor.getId()
            );
            deleteFollowConstructorUseCase.delete(followConstructor);
            updateConstructorUseCase.decreaseFollower(constructor);
            return;
        }

        createFollowConstructorUseCase.save(user, constructor);
        updateConstructorUseCase.increaseFollower(constructor);
    }

    @Override
    @Transactional
    public List<ResponseFollowConstructorDTO> getFollowingConstructorByUser(User user){
        Long userId = user.getId();

        if(saveFollowConstructorListUseCase.hasKey(userId)) return saveFollowConstructorListUseCase.getFollowingList(userId);

        List<ResponseFollowConstructorDTO> list = queryFollowConstructorUseCase.findAllByUserForDTO(userId);
        if(list.isEmpty()) return list;
        saveFollowConstructorListUseCase.saveFollowingList(userId, list);

        return list;
    }

    @Override
    @Transactional
    public List<ResponseFollowConstructorDTO> getFollowingConstructorByNickname(String nickname) {
        User user = queryUserUseCase.findByNickname(nickname);
        Long userId = user.getId();

        if(saveFollowConstructorListUseCase.hasKey(userId)) return saveFollowConstructorListUseCase.getFollowingList(userId);

        List<ResponseFollowConstructorDTO> list = queryFollowConstructorUseCase.findAllByUserForDTO(userId);
        if(list.isEmpty()) return list;
        saveFollowConstructorListUseCase.saveFollowingList(userId, list);

        return list;
    }
}
