package edu.java.repository.jpa;

import edu.java.domain.Link;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class JpaTelegramChatRepositoryTest extends IntegrationTest {

    @Autowired
    private JpaTelegramChatRepository telegramChatRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @Transactional
    @Rollback
    void setUp() {
        Long telegramChatId = 1L;

        jdbcTemplate.update("insert into chat (id) values (?)", telegramChatId);
    }

    @Test
    @Transactional
    @Rollback
    void removeChatLinkRelationships() {
        Link firstInsertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "firstUrl"
        );

        Link secondInsertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "secondUrl"
        );

        jdbcTemplate.update(
            "insert into chat_link (link_id, chat_id) VALUES (?, ?), (?, ?)",
            firstInsertedLink.getId(),
            1L,
            secondInsertedLink.getId(),
            1L
        );

        telegramChatRepository.removeChatLinkRelationships(1L);

        Integer expected = 0;
        Integer recordsCount = jdbcTemplate.queryForObject(
            "select count(*) from chat_link",
            Integer.class
        );

        assertEquals(expected, recordsCount);
    }

    @Test
    @Transactional
    @Rollback
    void getTelegramChatIdsByLinkId() {
        Link firstInsertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "firstUrl"
        );

        jdbcTemplate.update(
            "insert into chat_link (link_id, chat_id) VALUES (?, ?)",
            firstInsertedLink.getId(),
            1L
        );

        List<Long> chatIds = telegramChatRepository.getTelegramChatIdsByLinkId(firstInsertedLink.getId());
        assertEquals(chatIds.get(0), 1L);
    }
}
