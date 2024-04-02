package edu.java.bot.commandhandler;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import edu.java.dto.scrapper.request.AddLinkRequest;
import edu.java.dto.scrapper.response.LinkResponse;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TrackCommandHandlerTest {

    private static final String COMMAND = "/track";

    private static final String URL_FROM_MESSAGE = "https://stackoverflow.com/";

    private static final boolean CONTAINS_URL_IN_MESSAGE = true;

    private static final Long CHAT_ID = 1L;

    @Mock
    private static UpdateWrapper updateWrapper;

    @Mock
    private SendMessageService messageService;

    @Mock
    private ScrapperClient client;

    @Test
    void handleCommandWhenLinkIsNotInDB() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        Mockito.when(updateWrapper.getChatId()).thenReturn(CHAT_ID);
        Mockito.when(updateWrapper.getURLFromMessage()).thenReturn(URL_FROM_MESSAGE);
        Mockito.when(updateWrapper.containsUrlInMessage()).thenReturn(CONTAINS_URL_IN_MESSAGE);

        ResponseEntity<LinkResponse> responseEntity =
            new ResponseEntity<>(
                new LinkResponse(1L, URI.create(URL_FROM_MESSAGE)),
                HttpStatus.OK
            );
        Mockito.when(client.addLinkTracking(CHAT_ID, new AddLinkRequest(URI.create(URL_FROM_MESSAGE))))
            .thenReturn(responseEntity);

        TrackCommandHandler trackCommandHandler = new TrackCommandHandler(client, messageService);

        trackCommandHandler.handleCommand(updateWrapper);

        Mockito.verify(messageService).sendMessage(updateWrapper, "Начинаем отслеживать ссылку");
    }

    @Test
    void handleCommandWhenLinkIsInvalid() {
        String invalidUrl = "asd";
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        Mockito.when(updateWrapper.getURLFromMessage()).thenReturn(invalidUrl);
        Mockito.when(updateWrapper.containsUrlInMessage()).thenReturn(CONTAINS_URL_IN_MESSAGE);

        TrackCommandHandler trackCommandHandler = new TrackCommandHandler(client, messageService);

        trackCommandHandler.handleCommand(updateWrapper);

        Mockito.verify(messageService).sendMessage(updateWrapper, "Неверная ссылка");
    }
}
