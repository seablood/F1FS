package kr.co.F1FS.app.global.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import kr.co.F1FS.app.domain.note.domain.QNote;
import kr.co.F1FS.app.domain.post.domain.QPost;
import kr.co.F1FS.app.domain.reply.domain.QReply;
import kr.co.F1FS.app.domain.suggest.domain.QSuggest;

public class SoftDeleteExpressions {
    public static BooleanExpression isNotDeleted(QPost post){
        return post.deleted.isFalse();
    }

    public static BooleanExpression isNotDeleted(QNote note){
        return note.deleted.isFalse();
    }

    public static BooleanExpression isNotDeleted(QReply reply){
        return reply.deleted.isFalse();
    }

    public static BooleanExpression isNotDeleted(QSuggest suggest){
        return suggest.deleted.isFalse();
    }
}
