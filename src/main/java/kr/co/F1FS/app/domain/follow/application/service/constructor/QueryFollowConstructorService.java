package kr.co.F1FS.app.domain.follow.application.service.constructor;

import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.in.constructor.QueryFollowConstructorUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.constructor.FollowConstructorJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.presentation.dto.constructor.ResponseFollowConstructorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryFollowConstructorService implements QueryFollowConstructorUseCase {
    private final FollowConstructorJpaPort followConstructorJpaPort;
    private final FollowMapper followMapper;

    @Override
    public FollowConstructor findByUserAndConstructor(Long userId, Long constructorId) {
        return followConstructorJpaPort.findByUserAndConstructor(userId, constructorId);
    }

    @Override
    public List<ResponseFollowConstructorDTO> findAllByUserForDTO(Long userId) {
        return followConstructorJpaPort.findAllByUser(userId).stream()
                .map(followConstructor -> followConstructor.getFolloweeConstructor())
                .map(followeeConstructor -> followMapper.toResponseFollowConstructorDTO(followeeConstructor))
                .toList();
    }
}
