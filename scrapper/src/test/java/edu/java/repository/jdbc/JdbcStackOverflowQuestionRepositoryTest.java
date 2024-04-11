package edu.java.repository.jdbc;

import edu.java.domain.Link;
import edu.java.domain.StackOverflowQuestion;
import edu.java.repository.StackOverflowQuestionRepository;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class JdbcStackOverflowQuestionRepositoryTest extends IntegrationTest {
    @Autowired
    StackOverflowQuestionRepository stackOverflowQuestionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addStackOverflowQuestion() {
        StackOverflowQuestion stackOverflowQuestionToAdd = StackOverflowQuestion.builder()
            .questionId(1L)
            .title("title")
            .isAnswered(false)
            .score(0)
            .answerCount(0L)
            .lastActivityDate(OffsetDateTime.now())
            .build();

        Link insertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "link"
        );

        StackOverflowQuestion addedStackOverflowQuestion =
            stackOverflowQuestionRepository.addStackOverflowQuestion(stackOverflowQuestionToAdd, insertedLink.getId());

        StackOverflowQuestion stackOverflowQuestionFromDatabase = jdbcTemplate.queryForObject(
            "select * from stackoverflow_question where question_id = ?",
            new BeanPropertyRowMapper<>(StackOverflowQuestion.class),
            stackOverflowQuestionToAdd.getQuestionId()
        );

        assertEquals(addedStackOverflowQuestion, stackOverflowQuestionFromDatabase);
    }

    @Test
    @Transactional
    @Rollback
    void removeStackOverflowQuestions() {
        StackOverflowQuestion stackOverflowQuestionToAdd = StackOverflowQuestion.builder()
            .questionId(1L)
            .title("title")
            .isAnswered(false)
            .score(0)
            .answerCount(0L)
            .lastActivityDate(OffsetDateTime.now())
            .build();

        Link insertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "link"
        );

        StackOverflowQuestion insertedQuestion = jdbcTemplate.queryForObject(
            "insert into stackoverflow_question (question_id, link_id, title, is_answered, score,"
                + " answer_count, last_activity_date) VALUES (?, ?, ?, ?, ?, ?, ?) returning *",
            new BeanPropertyRowMapper<>(StackOverflowQuestion.class),
            stackOverflowQuestionToAdd.getQuestionId(),
            insertedLink.getId(),
            stackOverflowQuestionToAdd.getTitle(),
            stackOverflowQuestionToAdd.isAnswered(),
            stackOverflowQuestionToAdd.getScore(),
            stackOverflowQuestionToAdd.getAnswerCount(),
            stackOverflowQuestionToAdd.getLastActivityDate()
        );

        List<StackOverflowQuestion> removedStackOverflowQuestion =
            stackOverflowQuestionRepository.removeStackOverflowQuestions(insertedLink.getId());

        assertEquals(insertedQuestion, removedStackOverflowQuestion.get(0));
    }

    @Test
    @Transactional
    @Rollback
    void findStackOverflowQuestionsByLinkId() {
        StackOverflowQuestion stackOverflowQuestionToAdd = StackOverflowQuestion.builder()
            .questionId(1L)
            .title("title")
            .isAnswered(false)
            .score(0)
            .answerCount(0L)
            .lastActivityDate(OffsetDateTime.now())
            .build();

        Link insertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "link"
        );

        StackOverflowQuestion insertedQuestion = jdbcTemplate.queryForObject(
            "insert into stackoverflow_question (question_id, link_id, title, is_answered, score,"
                + " answer_count, last_activity_date) VALUES (?, ?, ?, ?, ?, ?, ?) returning *",
            new BeanPropertyRowMapper<>(StackOverflowQuestion.class),
            stackOverflowQuestionToAdd.getQuestionId(),
            insertedLink.getId(),
            stackOverflowQuestionToAdd.getTitle(),
            stackOverflowQuestionToAdd.isAnswered(),
            stackOverflowQuestionToAdd.getScore(),
            stackOverflowQuestionToAdd.getAnswerCount(),
            stackOverflowQuestionToAdd.getLastActivityDate()
        );

        List<StackOverflowQuestion> stackOverflowQuestionFromDatabase =
            stackOverflowQuestionRepository.findStackOverflowQuestionsByLinkId(insertedLink.getId());

        assertEquals(insertedQuestion, stackOverflowQuestionFromDatabase.get(0));
    }
}
