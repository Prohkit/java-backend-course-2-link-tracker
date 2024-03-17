package edu.java.repository;

import edu.java.domain.Chat;
import java.util.List;

public interface TelegramChatRepository {
    Chat addChat(Chat chat);

    Chat removeChat(Chat chat);

    List<Long> removeChatLinkRelationships(long telegramChatId);

    List<Chat> findAllChats();

    boolean isChatExists(Chat chat);

    List<Long> getTelegramChatIdsByLinkId(long linkId);
}
