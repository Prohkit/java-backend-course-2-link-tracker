package edu.java.bot.service;

import edu.java.dto.bot.LinkUpdate;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LinkUpdateServiceTest {
    @InjectMocks
    private LinkUpdateService linkUpdateService;

    @Mock
    private SendMessageService sendMessageService;

    @Test
    void sendNotification() {
        long telegramChatId = 1L;

        LinkUpdate linkUpdate = LinkUpdate.builder()
            .id(1L)
            .url(URI.create("link"))
            .description("description")
            .tgChatIds(List.of(telegramChatId))
            .build();

        linkUpdateService.sendNotification(linkUpdate);

        verify(sendMessageService, times(1)).sendLinkUpdateMessage(
            telegramChatId,
            linkUpdate.getUrl(),
            linkUpdate.getDescription()
        );
    }
}
