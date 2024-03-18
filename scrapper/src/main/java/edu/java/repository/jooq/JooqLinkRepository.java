package edu.java.repository.jooq;

import edu.java.domain.Link;
import edu.java.repository.LinkRepository;
import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.domain.jooq.tables.ChatLink.CHAT_LINK;
import static edu.java.domain.jooq.tables.Link.LINK;

@Repository
public class JooqLinkRepository implements LinkRepository {

    private final DSLContext dslContext;

    public JooqLinkRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public Link addLink(Link link) {
        return dslContext.insertInto(LINK, LINK.URL, LINK.LAST_MODIFIED_TIME)
            .values(link.getUrl().toString(), link.getLastModifiedTime())
            .returning(LINK.fields())
            .fetchOneInto(Link.class);
    }

    @Override
    @Transactional
    public void addChatLinkRelationship(long telegramChatId, long linkId) {
        dslContext.insertInto(CHAT_LINK, CHAT_LINK.CHAT_ID, CHAT_LINK.LINK_ID)
            .values(telegramChatId, linkId)
            .execute();
    }

    @Override
    @Transactional
    public boolean isChatLinkRelationshipExists(long telegramChatId, long linkId) {
        return dslContext.fetchExists(dslContext
            .selectFrom(CHAT_LINK)
            .where(CHAT_LINK.CHAT_ID.eq(telegramChatId))
            .and(CHAT_LINK.LINK_ID.eq(linkId)));
    }

    @Override
    @Transactional
    public void removeChatLinkRelationship(long telegramChatId, long linkId) {
        dslContext.deleteFrom(CHAT_LINK)
            .where(CHAT_LINK.CHAT_ID.eq(telegramChatId))
            .and(CHAT_LINK.LINK_ID.eq(linkId))
            .execute();
    }

    @Override
    @Transactional
    public boolean areThereAnyChatLinkRelationshipsByLinkId(Long linkId) {
        return dslContext.fetchExists(dslContext.selectFrom(CHAT_LINK)
            .where(CHAT_LINK.LINK_ID.eq(linkId)));
    }

    @Override
    @Transactional
    public Link removeLink(Link link) {
        return dslContext.deleteFrom(LINK)
            .where(LINK.URL.eq(link.getUrl().toString()))
            .returning(LINK.fields())
            .fetchOneInto(Link.class);
    }

    @Override
    @Transactional
    public List<Link> findAllLinks(long telegramChatId) {
        return dslContext.selectFrom(LINK.leftJoin(CHAT_LINK).on(LINK.ID.eq(CHAT_LINK.LINK_ID)))
            .where(CHAT_LINK.CHAT_ID.eq(telegramChatId))
            .fetchInto(Link.class);
    }

    @Override
    @Transactional
    public boolean isLinkExists(Link link) {
        return dslContext.fetchExists(dslContext.selectFrom(LINK)
            .where(LINK.URL.eq(link.getUrl().toString())));
    }

    @Override
    @Transactional
    public List<Link> findLinksToCheck(Timestamp sqlTimestamp) {
        return dslContext.selectFrom(LINK)
            .where(LINK.LAST_UPDATE_CHECK_TIME.cast(Timestamp.class).lessThan(sqlTimestamp))
            .fetchInto(Link.class);
    }

    @Override
    @Transactional
    public Link findLinkByUrl(URI url) {
        return dslContext.selectFrom(LINK)
            .where(LINK.URL.eq(url.toString()))
            .fetchOneInto(Link.class);
    }

    @Override
    @Transactional
    public Link findLinkById(long linkId) {
        return dslContext.selectFrom(LINK)
            .where(LINK.ID.eq(linkId))
            .fetchOneInto(Link.class);
    }

    @Override
    @Transactional
    public void updateLinkLastModifiedTime(Link link) {
        dslContext.update(LINK)
            .set(LINK.LAST_MODIFIED_TIME, link.getLastModifiedTime())
            .where(LINK.ID.eq(link.getId()))
            .execute();
    }

    @Override
    @Transactional
    public void updateLinkLastUpdateCheckTime(Link link) {
        dslContext.update(LINK)
            .set(LINK.LAST_UPDATE_CHECK_TIME, OffsetDateTime.now())
            .where(LINK.ID.eq(link.getId()))
            .execute();
    }
}
