package edu.java.bot.commandhandler;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class StartCommandHandlerTest {

    private static final String COMMAND = "/start";
    private static final Long CHAT_ID = 1L;

    @Mock
    private SendMessageService messageService;

    @Mock
    private ScrapperClient client;

    @Mock
    private UpdateWrapper updateWrapper;

    @Test
    void handleCommand() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        Mockito.when(updateWrapper.getChatId()).thenReturn(CHAT_ID);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(client.registerChat(CHAT_ID)).thenReturn(responseEntity);
        StartCommandHandler startCommandHandler = new StartCommandHandler(messageService, client);

        startCommandHandler.handleCommand(updateWrapper);

        Mockito.verify(messageService).sendMessage(updateWrapper, "Зарегистрирован новый пользователь");
    }
}
