package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.FollowConstructor;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.FollowConstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowConstructorService {
    private final FollowConstructorRepository followConstructorRepository;
    private final UserService userService;
    private final ConstructorService constructorService;

    @Transactional
    public void toggle(User user, Long id){
        Constructor constructor = constructorService.findByIdNotDTO(id);

        if(isFollowed(user, constructor)){
            FollowConstructor followConstructor = followConstructorRepository.findByFollowerUserAndFolloweeConstructor(
                    user, constructor
            );
            followConstructorRepository.delete(followConstructor);
            constructor.decreaseFollower();
            return;
        }

        FollowConstructor followConstructor = FollowConstructor.builder()
                .followerUser(user)
                .followeeConstructor(constructor)
                .build();
        followConstructorRepository.save(followConstructor);
        constructor.increaseFollower();
    }

    public List<SimpleResponseConstructorDTO> getFollowingConstructor(String nickname){
        User user = userService.findByNicknameNotDTO(nickname);
        return followConstructorRepository.findByFollowerUser(user).stream()
                .map(followConstructor -> followConstructor.getFolloweeConstructor())
                .map(followeeConstructor -> SimpleResponseConstructorDTO.toDto(followeeConstructor))
                .toList();
    }

    public List<SimpleResponseConstructorDTO> getFollowingConstructorAuth(User user){
        return followConstructorRepository.findByFollowerUser(user).stream()
                .map(followConstructor -> followConstructor.getFolloweeConstructor())
                .map(followeeConstructor -> SimpleResponseConstructorDTO.toDto(followeeConstructor))
                .toList();
    }

    public boolean isFollowed(User user, Constructor constructor){
        return followConstructorRepository.existsFollowConstructorByFollowerUserAndFolloweeConstructor(
                user, constructor);
    }
}
