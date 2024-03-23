package edu.java.repository.jdbc;

import edu.java.domain.Chat;
import edu.java.repository.TelegramChatRepository;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Primary
public class JdbcTelegramChatRepository implements TelegramChatRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTelegramChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Chat addChat(Chat chat) {
        return jdbcTemplate.queryForObject(
            "insert into chat (id) values (?) returning *",
            new BeanPropertyRowMapper<>(Chat.class),
            chat.getId()
        );
    }

    @Override
    @Transactional
    public Chat removeChat(Chat chat) {
        return jdbcTemplate.queryForObject(
            "delete from chat where id = ? returning *",
            new BeanPropertyRowMapper<>(Chat.class),
            chat.getId()
        );
    }

    @Override
    public List<Long> removeChatLinkRelationships(long telegramChatId) {
        return jdbcTemplate.query(
            "delete from chat_link where chat_id = ? returning link_id",
            new BeanPropertyRowMapper<>(Long.class),
            telegramChatId
        );
    }

    @Override
    @Transactional
    public List<Chat> findAllChats() {
        return jdbcTemplate.query("select * from chat", new BeanPropertyRowMapper<>(Chat.class));
    }

    @Override
    @Transactional
    public boolean isChatExists(Chat chat) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
            "select exists (select true from chat where id = ?)",
            Boolean.class,
            chat.getId()
        ));
    }

    @Override
    public List<Long> getTelegramChatIdsByLinkId(long linkId) {
        return jdbcTemplate.queryForList(
            "select chat_id from chat_link where link_id = ?",
            Long.class,
            linkId
        );
    }
}
