package kr.co.F1FS.app.domain.follow.application.mapper;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowConstructorDTO;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowUserDTO;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FollowMapper {
    public FollowConstructor toFollowConstructor(User user, Constructor constructor){
        return FollowConstructor.builder()
                .followerUser(user)
                .followeeConstructor(constructor)
                .build();
    }

    public FollowDriver toFollowDriver(User user, Driver driver){
        return FollowDriver.builder()
                .followerUser(user)
                .followeeDriver(driver)
                .build();
    }

    public FollowUser toFollowUser(User followerUser, User followeeUser){
        return FollowUser.builder()
                .followerUser(followerUser)
                .followeeUser(followeeUser)
                .build();
    }

    public ResponseFollowConstructorDTO toResponseFollowConstructorDTO(Constructor constructor){
        List<String> list = new ArrayList<>();

        for (ConstructorDriverRelation relation : constructor.getDrivers()){
            list.add(relation.getDriver().getName());
        }

        return ResponseFollowConstructorDTO.builder()
                .name(constructor.getName())
                .engName(constructor.getEngName())
                .driverList(list)
                .build();
    }

    public ResponseFollowDriverDTO toResponseFollowDriverDTO(Driver driver){
        return ResponseFollowDriverDTO.builder()
                .name(driver.getName())
                .engName(driver.getEngName())
                .number(driver.getNumber())
                .team(driver.getTeam())
                .build();
    }

    public ResponseFollowUserDTO toResponseFollowUserDTO(User user){
        return ResponseFollowUserDTO.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .build();
    }

    public ResponseUserIdDTO toResponseUserIdDTO(User followerUser){
        return ResponseUserIdDTO.builder()
                .id(followerUser.getId())
                .build();
    }
}
