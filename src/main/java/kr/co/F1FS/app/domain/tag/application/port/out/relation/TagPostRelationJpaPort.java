package kr.co.F1FS.app.domain.tag.application.port.out.relation;

import kr.co.F1FS.app.domain.tag.domain.TagPostRelation;

public interface TagPostRelationJpaPort {
    TagPostRelation save(TagPostRelation relation);
}
