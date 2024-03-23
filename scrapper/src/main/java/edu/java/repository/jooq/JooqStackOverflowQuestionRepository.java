package edu.java.repository.jooq;

import edu.java.domain.StackOverflowQuestion;
import edu.java.repository.StackOverflowQuestionRepository;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.domain.jooq.tables.StackoverflowQuestion.STACKOVERFLOW_QUESTION;

@Repository
public class JooqStackOverflowQuestionRepository implements StackOverflowQuestionRepository {

    private final DSLContext dslContext;

    public JooqStackOverflowQuestionRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public StackOverflowQuestion addStackOverflowQuestion(StackOverflowQuestion stackOverflowQuestion, Long linkId) {
        return dslContext.insertInto(
                STACKOVERFLOW_QUESTION,
                STACKOVERFLOW_QUESTION.ID,
                STACKOVERFLOW_QUESTION.LINK_ID,
                STACKOVERFLOW_QUESTION.TITLE,
                STACKOVERFLOW_QUESTION.IS_ANSWERED,
                STACKOVERFLOW_QUESTION.SCORE,
                STACKOVERFLOW_QUESTION.ANSWER_COUNT,
                STACKOVERFLOW_QUESTION.LAST_ACTIVITY_DATE
            )
            .values(
                stackOverflowQuestion.getQuestionId(),
                linkId,
                stackOverflowQuestion.getTitle(),
                stackOverflowQuestion.isAnswered(),
                stackOverflowQuestion.getScore(),
                stackOverflowQuestion.getAnswerCount(),
                stackOverflowQuestion.getLastActivityDate()
            )
            .returning(STACKOVERFLOW_QUESTION.fields())
            .fetchOneInto(StackOverflowQuestion.class);
    }

    @Override
    @Transactional
    public List<StackOverflowQuestion> removeStackOverflowQuestions(Long linkId) {
        return dslContext.deleteFrom(STACKOVERFLOW_QUESTION)
            .where(STACKOVERFLOW_QUESTION.LINK_ID.eq(linkId))
            .returning(STACKOVERFLOW_QUESTION.fields())
            .fetchInto(StackOverflowQuestion.class);
    }

    @Override
    @Transactional
    public List<StackOverflowQuestion> findStackOverflowQuestionsByLinkId(Long linkId) {
        return dslContext.selectFrom(STACKOVERFLOW_QUESTION)
            .where(STACKOVERFLOW_QUESTION.LINK_ID.eq(linkId))
            .fetchInto(StackOverflowQuestion.class);
    }
}
