package kr.co.F1FS.app.domain.repository.elastic;

import kr.co.F1FS.app.domain.model.elastic.DriverDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DriverSearchRepository extends ElasticsearchRepository<DriverDocument, Long> {
    Page<DriverDocument> findByKorNameContainingOrEngNameContainingOrTeamContainingOrEngTeamContaining(
            String korName, String engName, String team, String engTeam, Pageable pageable
    );
}
