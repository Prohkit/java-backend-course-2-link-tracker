package edu.java.repository.jooq;

import edu.java.domain.Chat;
import edu.java.repository.TelegramChatRepository;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.domain.jooq.tables.Chat.CHAT;
import static edu.java.domain.jooq.tables.ChatLink.CHAT_LINK;

@Repository
public class JooqTelegramChatRepository implements TelegramChatRepository {

    private final DSLContext dslContext;

    public JooqTelegramChatRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public Chat addChat(Chat chat) {
        return dslContext.insertInto(CHAT, CHAT.ID)
            .values(chat.getId())
            .returning(CHAT.fields())
            .fetchOneInto(Chat.class);
    }

    @Override
    @Transactional
    public Chat removeChat(Chat chat) {
        return dslContext.deleteFrom(CHAT)
            .where(CHAT.ID.eq(chat.getId()))
            .returning(CHAT.fields())
            .fetchOneInto(Chat.class);
    }

    @Override
    @Transactional
    public List<Long> removeChatLinkRelationships(long telegramChatId) {
        return dslContext.deleteFrom(CHAT_LINK)
            .where(CHAT_LINK.CHAT_ID.eq(telegramChatId))
            .returning(CHAT_LINK.LINK_ID)
            .fetchInto(Long.class);
    }

    @Override
    @Transactional
    public List<Chat> findAllChats() {
        return dslContext.selectFrom(CHAT)
            .fetchInto(Chat.class);
    }

    @Override
    @Transactional
    public boolean isChatExists(Chat chat) {
        return dslContext.fetchExists(dslContext.selectFrom(CHAT)
            .where(CHAT.ID.eq(chat.getId())));
    }

    @Override
    @Transactional
    public List<Long> getTelegramChatIdsByLinkId(long linkId) {
        return dslContext.select(CHAT_LINK.CHAT_ID)
            .from(CHAT_LINK)
            .where(CHAT_LINK.LINK_ID.eq(linkId))
            .fetchInto(Long.class);
    }
}
