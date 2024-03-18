package edu.java.repository.jdbc;

import edu.java.domain.Link;
import edu.java.repository.LinkRepository;
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

@SpringBootTest
class JdbcLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @Transactional
    @Rollback
    void setUp() {
        Long telegramChatId = 100L;

        jdbcTemplate.update("insert into chat (id) values (?)", telegramChatId);
    }

    @Test
    @Transactional
    @Rollback
    void addLink() {
        URI url = URI.create("firstUrl");
        Link linkToAdd = Link.builder().url(url).build();

        Link addedLink = linkRepository.addLink(linkToAdd);
        Link linkFromDatabase = jdbcTemplate.queryForObject(
            "select * from link where id = ?",
            new BeanPropertyRowMapper<>(Link.class),
            addedLink.getId()
        );
        assertEquals(addedLink, linkFromDatabase);
    }

    @Test
    @Transactional
    @Rollback
    void removeLink() {
        URI url = URI.create("firstUrl");
        Link linkToRemove = Link.builder().url(url).build();

        Link insertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            linkToRemove.getUrl().toString()
        );

        Link removedLink = linkRepository.removeLink(linkToRemove);
        assertEquals(insertedLink, removedLink);
        assertEquals(removedLink.getUrl(), linkToRemove.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    void findAllLinks() {
        Long telegramChatId = 100L;
        String firstUrl = "firstUrl";
        String secondUrl = "secondUrl";

        Link firstInsertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            firstUrl
        );

        Link secondInsertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            secondUrl
        );

        jdbcTemplate.update(
            "insert into chat_link (chat_id, link_id) values (?, ?), (?, ?)",
            telegramChatId,
            firstInsertedLink.getId(),
            telegramChatId,
            secondInsertedLink.getId()
        );

        List<Link> linkList = linkRepository.findAllLinks(telegramChatId);

        assertThat(linkList)
            .hasSizeGreaterThanOrEqualTo(2)
            .containsAnyOf(firstInsertedLink, secondInsertedLink);
    }

    @Test
    @Transactional
    @Rollback
    void isLinkExistsByUrlAndChatId() {
        URI url = URI.create("firstUrl");
        Link linkToAdd = Link.builder().url(url).build();

        assertFalse(linkRepository.isLinkExists(linkToAdd));

        jdbcTemplate.update(
            "insert into link (url) values (?)",
            linkToAdd.getUrl().toString()
        );

        assertTrue(linkRepository.isLinkExists(linkToAdd));
    }
}
