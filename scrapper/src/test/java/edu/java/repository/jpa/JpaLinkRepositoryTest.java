package edu.java.repository.jpa;

import edu.java.domain.Link;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class JpaLinkRepositoryTest extends IntegrationTest {

    @Autowired
    private JpaLinkRepository linkRepository;

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
    void existsLinkByUrl() {
        URI url = URI.create("firstUrl");
        Link linkToAdd = Link.builder().url(url).build();

        assertFalse(linkRepository.existsLinkByUrl(linkToAdd.getUrl().toString()));

        jdbcTemplate.update(
            "insert into link (url) values (?)",
            linkToAdd.getUrl().toString()
        );

        assertTrue(linkRepository.existsLinkByUrl(linkToAdd.getUrl().toString()));
    }

    @Test
    @Transactional
    @Rollback
    void findLinkByUrl() {
        URI url = URI.create("firstUrl");
        Link linkToAdd = Link.builder().url(url).build();

        jdbcTemplate.update(
            "insert into link (url) values (?)",
            linkToAdd.getUrl().toString()
        );

        Link link = linkRepository.findLinkByUrl(url);

        assertEquals(link.getUrl(), url);
    }

    @Test
    @Transactional
    @Rollback
    void isChatLinkRelationshipExists() {
        Link firstInsertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "firstUrl"
        );
        assertFalse(linkRepository.isChatLinkRelationshipExists(1L, 1L));

        jdbcTemplate.update(
            "insert into chat_link (link_id, chat_id) VALUES (?, ?)",
            firstInsertedLink.getId(),
            1L
        );

        assertTrue(linkRepository.isChatLinkRelationshipExists(1L, firstInsertedLink.getId()));
    }

    @Test
    @Transactional
    @Rollback
    void addChatLinkRelationship() {
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

        Link thirdInsertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "thirdUrl"
        );

        linkRepository.addChatLinkRelationship(1L, firstInsertedLink.getId());
        linkRepository.addChatLinkRelationship(1L, secondInsertedLink.getId());
        linkRepository.addChatLinkRelationship(1L, thirdInsertedLink.getId());

        Integer expected = 3;
        Integer recordsCount = jdbcTemplate.queryForObject(
            "select count(*) from chat_link",
            Integer.class
        );

        assertEquals(expected, recordsCount);
    }

    @Test
    @Transactional
    @Rollback
    void removeChatLinkRelationship() {
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

        Integer expectedBeforeRemove = 1;
        Integer recordsCountBeforeRemove = jdbcTemplate.queryForObject(
            "select count(*) from chat_link",
            Integer.class
        );

        assertEquals(expectedBeforeRemove, recordsCountBeforeRemove);

        linkRepository.removeChatLinkRelationship(1L, firstInsertedLink.getId());

        Integer expectedAfterRemove = 0;
        Integer recordsCountAfterRemove = jdbcTemplate.queryForObject(
            "select count(*) from chat_link",
            Integer.class
        );

        assertEquals(expectedAfterRemove, recordsCountAfterRemove);
    }

    @Test
    @Transactional
    @Rollback
    void areThereAnyChatLinkRelationshipsByLinkId() {
        assertFalse(linkRepository.areThereAnyChatLinkRelationshipsByLinkId(1L));

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

        assertTrue(linkRepository.areThereAnyChatLinkRelationshipsByLinkId(firstInsertedLink.getId()));
    }

    @Test
    @Transactional
    @Rollback
    void findLinksByChatId() {
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
            "insert into chat_link (link_id, chat_id) VALUES (?, ?)",
            firstInsertedLink.getId(),
            1L
        );

        jdbcTemplate.update(
            "insert into chat_link (link_id, chat_id) VALUES (?, ?)",
            secondInsertedLink.getId(),
            1L
        );

        List<Link> linkList = linkRepository.findLinksByChatId(1L);
        List<String> urlList = linkList.stream().map(link -> link.getUrl().toString()).toList();
        assertThat(urlList)
            .hasSizeGreaterThanOrEqualTo(2)
            .containsAnyOf(firstInsertedLink.getUrl().toString(), secondInsertedLink.getUrl().toString());
    }
}
