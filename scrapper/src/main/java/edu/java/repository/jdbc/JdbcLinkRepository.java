package edu.java.repository.jdbc;

import edu.java.domain.Link;
import edu.java.repository.LinkRepository;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLinkRepository implements LinkRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Link addLink(Link link) {
        return jdbcTemplate.queryForObject(
            "insert into link (url, last_modified_time) values (?, now()) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            link.getUrl().toString()
        );
    }

    @Override
    @Transactional
    public void addChatLinkRelationship(long telegramChatId, long linkId) {
        jdbcTemplate.update("insert into chat_link (chat_id, link_id) values (?, ?)", telegramChatId, linkId);
    }

    @Override
    @Transactional
    public boolean isChatLinkRelationshipExists(long telegramChatId, long linkId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
            "select exists (select true from chat_link where chat_id = ? and link_id = ?)",
            Boolean.class,
            telegramChatId,
            linkId
        ));
    }

    @Override
    @Transactional
    public Link removeLink(Link link) {
        return jdbcTemplate.queryForObject(
            "delete from link where url = ? returning *",
            new BeanPropertyRowMapper<>(Link.class),
            link.getUrl().toString()
        );
    }

    @Override
    @Transactional
    public boolean areThereAnyChatLinkRelationshipsByLinkId(Long linkId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
            "select exists (select true from chat_link where link_id = ?)",
            Boolean.class,
            linkId
        ));
    }

    @Override
    @Transactional
    public void removeChatLinkRelationship(long telegramChatId, long linkId) {
        jdbcTemplate.update("delete from chat_link where chat_id = ? and link_id = ?", telegramChatId, linkId);
    }

    @Override
    @Transactional
    public List<Link> findAllLinks(long telegramChatId) {
        return jdbcTemplate.query(
            "select * from link left join chat_link cl on link.id = cl.link_id where cl.chat_id = ?",
            new BeanPropertyRowMapper<>(Link.class),
            telegramChatId
        );
    }

    @Override
    @Transactional
    public boolean isLinkExists(Link link) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
            "select exists (select true from link where url = ?)",
            Boolean.class,
            link.getUrl().toString()
        ));
    }

    @Override
    @Transactional
    public List<Link> findLinksToCheck(Timestamp timeToCheck) {
        return jdbcTemplate.query(
            "select * from link where link.last_update_check_time < ?",
            new BeanPropertyRowMapper<>(Link.class),
            timeToCheck
        );
    }

    @Override
    @Transactional
    public Link findLinkByUrl(URI url) {
        return jdbcTemplate.queryForObject(
            "select * from link where url = ?",
            new BeanPropertyRowMapper<>(Link.class),
            url.toString()
        );
    }

    @Override
    @Transactional
    public Link findLinkById(long linkId) {
        return jdbcTemplate.queryForObject(
            "select * from link where id = ?",
            new BeanPropertyRowMapper<>(Link.class),
            linkId
        );
    }

    @Override
    @Transactional
    public void updateLinkLastModifiedTime(Link link) {
        jdbcTemplate.update(
            "update link set last_modified_time = ? where id = ?",
            link.getLastModifiedTime(),
            link.getId()
        );
    }

    @Override
    @Transactional
    public void updateLinkLastUpdateCheckTime(Link link) {
        jdbcTemplate.update("update link set last_update_check_time = now() where id = ?", link.getId());
    }
}
