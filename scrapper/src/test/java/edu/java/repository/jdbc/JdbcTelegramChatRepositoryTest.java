package edu.java.repository.jdbc;

import edu.java.domain.Chat;
import edu.java.repository.TelegramChatRepository;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
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
class JdbcTelegramChatRepositoryTest extends IntegrationTest {

    @Autowired
    private TelegramChatRepository chatRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addChat() {
        Long telegramChatId = 100L;
        Chat chatToAdd = Chat.builder().id(telegramChatId).build();

        chatRepository.addChat(chatToAdd);

        Chat insertedChat =
            jdbcTemplate.queryForObject(
                "select * from chat where id = ?",
                new BeanPropertyRowMapper<>(Chat.class),
                chatToAdd.getId()
            );

        assertEquals(chatToAdd.getId(), insertedChat.getId());
    }

    @Test
    @Transactional
    @Rollback
    void removeChat() {
        Long telegramChatId = 100L;
        Chat chatToRemove = Chat.builder().id(telegramChatId).build();

        Chat insertedChat =
            jdbcTemplate.queryForObject(
                "insert into chat (id) values (?) returning *",
                new BeanPropertyRowMapper<>(Chat.class),
                telegramChatId
            );

        Chat removedChat = chatRepository.removeChat(chatToRemove);

        assertEquals(insertedChat, removedChat);
        assertEquals(removedChat.getId(), chatToRemove.getId());
    }

    @Test
    @Transactional
    @Rollback
    void findAllChats() {
        Long firstTelegramChatId = 100L;
        Long secondTelegramChatId = 101L;

        Chat firstInsertedChat =
            jdbcTemplate.queryForObject(
                "insert into chat (id) values (?) returning *",
                new BeanPropertyRowMapper<>(Chat.class),
                firstTelegramChatId
            );

        Chat secondInsertedChat =
            jdbcTemplate.queryForObject(
                "insert into chat (id) values (?) returning *",
                new BeanPropertyRowMapper<>(Chat.class),
                secondTelegramChatId
            );

        List<Chat> chatList = chatRepository.findAllChats();

        assertThat(chatList)
            .hasSizeGreaterThanOrEqualTo(2)
            .containsAnyOf(firstInsertedChat, secondInsertedChat);
    }

    @Test
    @Transactional
    @Rollback
    void isChatExists() {
        Long telegramChatId = 100L;
        Chat chatToAdd = Chat.builder().id(telegramChatId).build();

        assertFalse(chatRepository.isChatExists(chatToAdd));

        jdbcTemplate.update(
            "insert into chat (id) values (?)",
            telegramChatId
        );

        assertTrue(chatRepository.isChatExists(chatToAdd));
    }
}
