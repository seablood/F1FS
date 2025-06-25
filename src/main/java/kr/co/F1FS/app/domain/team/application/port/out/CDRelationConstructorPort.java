package kr.co.F1FS.app.domain.team.application.port.out;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;

public interface CDRelationConstructorPort {
    Constructor findByName(String name);
}
