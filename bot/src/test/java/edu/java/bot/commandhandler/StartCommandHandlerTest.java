package edu.java.bot.commandhandler;

import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

@ExtendWith(MockitoExtension.class)
class StartCommandHandlerTest {

    private static final String COMMAND = "/start";

    @Mock
    private SendMessageService messageService;

    @Mock
    private UpdateWrapper updateWrapper;

    @Test
    void handleCommand() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        StartCommandHandler startCommandHandler = new StartCommandHandler(messageService);

        startCommandHandler.handleCommand(updateWrapper);

        Mockito.verify(messageService).sendMessage(updateWrapper, "Зарегистрирован новый пользователь");
    }
}
