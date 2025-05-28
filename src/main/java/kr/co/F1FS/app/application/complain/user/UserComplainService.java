package kr.co.F1FS.app.application.complain.user;

import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.model.rdb.UserComplain;
import kr.co.F1FS.app.domain.repository.rdb.complain.UserComplainRepository;
import kr.co.F1FS.app.presentation.admin.user.dto.ResponseUserComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserComplainService {
    private final UserComplainRepository complainRepository;

    public void save(UserComplain complain){
        complainRepository.save(complain);
    }

    public Page<ResponseUserComplainDTO> findAll(Pageable pageable){
        return complainRepository.findAll(pageable).map(complain -> ResponseUserComplainDTO.toDto(complain));
    }

    public Page<ResponseUserComplainDTO> getComplainByUser(User toUser, Pageable pageable){
        return complainRepository.findAllByToUser(toUser, pageable)
                .map(complain -> ResponseUserComplainDTO.toDto(complain));
    }

    public void deleteComplainByUser(User user){
        List<UserComplain> list = complainRepository.findAllByToUser(user);
        if(!list.isEmpty()){
            list.stream().forEach(complain -> complainRepository.delete(complain));
        }
    }
}
