package edu.java.bot.service;

import edu.java.dto.bot.LinkUpdate;
import io.micrometer.core.instrument.Counter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUpdateService {
    private final Counter processedMessagesCounter;
    private final SendMessageService sendMessageService;

    public void sendNotification(LinkUpdate linkUpdate) {
        List<Long> tgChatIds = linkUpdate.getTgChatIds();
        if (tgChatIds.isEmpty()) {
            throw new IllegalStateException();
        }
        for (Long telegramChatId : linkUpdate.getTgChatIds()) {
            sendMessageService.sendLinkUpdateMessage(telegramChatId, linkUpdate.getUrl(), linkUpdate.getDescription());
            processedMessagesCounter.increment();
        }
    }
}
