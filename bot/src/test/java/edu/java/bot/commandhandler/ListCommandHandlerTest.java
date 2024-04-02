package edu.java.bot.commandhandler;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ListCommandHandlerTest {
    private static final String COMMAND = "/list";
    private static final Long CHAT_ID = 1L;

    @Mock
    private UpdateWrapper updateWrapper;

    @Mock
    private SendMessageService messageService;

    @Mock
    private ScrapperClient client;

    @Test
    void handleCommandWhenLinksAreInDB() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        Mockito.when(updateWrapper.getChatId()).thenReturn(CHAT_ID);
        ResponseEntity<ListLinksResponse> responseEntity =
            new ResponseEntity<>(
                new ListLinksResponse(
                    List.of(
                        new LinkResponse(
                            1L,
                            URI.create("https://stackoverflow.com/")
                        )), 1),
                HttpStatus.OK
            );
        Mockito.when(client.getAllTrackedLinks(CHAT_ID)).thenReturn(responseEntity);

        ListCommandHandler listCommandHandler = new ListCommandHandler(client, messageService);

        listCommandHandler.handleCommand(updateWrapper);

        Mockito.verify(messageService)
            .sendMessage(updateWrapper, "Количество отслеживаемых ссылок: 1"
                + System.lineSeparator()
                + "Список отслеживаемых ссылок: "
                + System.lineSeparator()
                + "https://stackoverflow.com/"
                + System.lineSeparator());
    }

    @Test
    void handleCommandWhenDBIsEmpty() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        Mockito.when(updateWrapper.getChatId()).thenReturn(CHAT_ID);
        ResponseEntity<ListLinksResponse> responseEntity =
            new ResponseEntity<>(
                new ListLinksResponse(new ArrayList<>(), 0),
                HttpStatus.OK
            );
        Mockito.when(client.getAllTrackedLinks(CHAT_ID)).thenReturn(responseEntity);

        ListCommandHandler listCommandHandler = new ListCommandHandler(client, messageService);

        listCommandHandler.handleCommand(updateWrapper);

        Mockito.verify(messageService).sendMessage(updateWrapper, "Нет отслеживаемых ссылок.");
    }
}
