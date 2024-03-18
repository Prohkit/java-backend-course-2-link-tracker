package edu.java.service;

import java.util.List;

public interface TelegramChatService {
    void register(long telegramChatId);

    void unregister(long telegramChatId);

    boolean isChatExists(long telegramChatId);

    List<Long> getTelegramChatIdsByLinkId(long linkId);
}
