package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.FollowConstructor;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.FollowConstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowConstructorService {
    private final FollowConstructorRepository followConstructorRepository;
    private final UserService userService;
    private final ConstructorService constructorService;

    @Transactional
    public void toggle(String username, String name){
        User user = userService.findByUsernameNotDTO(username);
        Constructor constructor = constructorService.findByName(name);

        if(isFollowed(user, constructor)){
            FollowConstructor followConstructor = followConstructorRepository.findByFollowerUserAndFolloweeConstructor(
                    user, constructor
            );
            followConstructorRepository.delete(followConstructor);
            constructor.decreaseFollower();
        }

        else {
            FollowConstructor followConstructor = FollowConstructor.builder()
                    .followerUser(user)
                    .followeeConstructor(constructor)
                    .build();
            followConstructorRepository.save(followConstructor);
            constructor.increaseFollower();
        }
    }

    public boolean isFollowed(User user, Constructor constructor){
        return followConstructorRepository.existsFollowConstructorByFollowerUserAndFolloweeConstructor(
                user, constructor);
    }
}
