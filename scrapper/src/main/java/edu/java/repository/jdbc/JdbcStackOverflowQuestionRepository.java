package edu.java.repository.jdbc;

import edu.java.domain.StackOverflowQuestion;
import edu.java.repository.StackOverflowQuestionRepository;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Primary
public class JdbcStackOverflowQuestionRepository implements StackOverflowQuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcStackOverflowQuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public StackOverflowQuestion addStackOverflowQuestion(StackOverflowQuestion stackOverflowQuestion, Long linkId) {
        String insertSql =
            "insert into stackoverflow_question (question_id, link_id, title, is_answered, score,"
                + " answer_count, last_activity_date) VALUES (?, ?, ?, ?, ?, ?, ?) returning *";
        return jdbcTemplate.queryForObject(
            insertSql,
            new BeanPropertyRowMapper<>(StackOverflowQuestion.class),
            stackOverflowQuestion.getQuestionId(),
            linkId,
            stackOverflowQuestion.getTitle(),
            stackOverflowQuestion.isAnswered(),
            stackOverflowQuestion.getScore(),
            stackOverflowQuestion.getAnswerCount(),
            stackOverflowQuestion.getLastActivityDate()
        );
    }

    @Override
    @Transactional
    public List<StackOverflowQuestion> removeStackOverflowQuestions(Long linkId) {
        return jdbcTemplate.query(
            "delete from stackoverflow_question where link_id = ? returning *",
            new BeanPropertyRowMapper<>(StackOverflowQuestion.class),
            linkId
        );
    }

    @Override
    @Transactional
    public List<StackOverflowQuestion> findStackOverflowQuestionsByLinkId(Long linkId) {
        return jdbcTemplate.query(
            "select * from stackoverflow_question where link_id = ?",
            new BeanPropertyRowMapper<>(StackOverflowQuestion.class),
            linkId
        );
    }
}
