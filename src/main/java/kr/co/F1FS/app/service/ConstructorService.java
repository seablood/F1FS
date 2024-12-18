package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.CreateConstructorDTO;
import kr.co.F1FS.app.dto.ResponseConstructorDTO;
import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.repository.ConstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructorService {
    private final ConstructorRepository constructorRepository;

    @Transactional
    public Constructor save(CreateConstructorDTO dto){
        Constructor constructor = CreateConstructorDTO.toEntity(dto);
        return constructorRepository.saveAndFlush(constructor);
    }

    public ResponseConstructorDTO findById(Long id){
        Constructor constructor = constructorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Constructor를 찾지 못했습니다."));
        return ResponseConstructorDTO.toDto(constructor, getDrivers(constructor));
    }

    public List<String> getDrivers(Constructor constructor){
        return constructor.getDrivers().stream()
                .map((relation) -> relation.getDriver().getName())
                .toList();
    }
}
