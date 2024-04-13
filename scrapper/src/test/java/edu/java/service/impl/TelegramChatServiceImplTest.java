package edu.java.service.impl;

import edu.java.domain.Chat;
import edu.java.domain.Link;
import edu.java.repository.LinkRepository;
import edu.java.repository.TelegramChatRepository;
import edu.java.service.AdditionalInfoService;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramChatServiceImplTest {
    @InjectMocks
    private TelegramChatServiceImpl telegramChatService;

    @Mock
    private TelegramChatRepository telegramChatRepository;

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private AdditionalInfoService additionalInfoService;

    @Test
    void register() {
        long telegramChatId = 1L;

        Chat chat = Chat.builder().id(telegramChatId).build();

        telegramChatService.register(telegramChatId);

        verify(telegramChatRepository, times(1)).addChat(chat);
    }

    @Test
    void unregister_shouldRemoveChat() {
        long telegramChatId = 1L;

        Chat chat = Chat.builder().id(telegramChatId).build();

        telegramChatService.unregister(telegramChatId);

        verify(telegramChatRepository, times(1)).removeChat(chat);
    }

    @Test
    void unregister_shouldRemoveLinkAndAdditionalInfo_whenChatLinkRelationshipDoesNotExist() {
        long telegramChatId = 1L;
        long linkId = 1L;

        Link link = Link.builder().url(URI.create("link")).build();

        when(telegramChatRepository.removeChatLinkRelationships(telegramChatId)).thenReturn(List.of(linkId));
        when(linkRepository.areThereAnyChatLinkRelationshipsByLinkId(linkId)).thenReturn(false);
        when(linkRepository.findLinkById(linkId)).thenReturn(link);

        telegramChatService.unregister(telegramChatId);

        verify(linkRepository, times(1)).findLinkById(linkId);
        verify(linkRepository, times(1)).removeLink(link);
        verify(additionalInfoService, times(1)).removeAdditionalInfo(link);
    }

    @Test
    void isChatExists() {
        long telegramChatId = 1L;

        Chat chat = Chat.builder().id(telegramChatId).build();

        telegramChatService.isChatExists(telegramChatId);

        verify(telegramChatRepository, times(1)).isChatExists(chat);
    }

    @Test
    void getTelegramChatIdsByLinkId() {
        long linkId = 1L;

        telegramChatService.getTelegramChatIdsByLinkId(linkId);

        verify(telegramChatRepository, times(1)).getTelegramChatIdsByLinkId(linkId);
    }
}
