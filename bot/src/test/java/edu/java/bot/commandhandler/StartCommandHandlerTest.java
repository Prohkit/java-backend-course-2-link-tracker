package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class StartCommandHandlerTest {

    private final static String COMMAND = "/start";

    @Mock
    private UpdateWrapper updateWrapper;

    @Test
    void handleCommand() {
        Long id = 1L;
        Mockito.when(updateWrapper.getChatId()).thenReturn(id);
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        TelegramBot telegramBot = new TelegramBot("token");
        StartCommandHandler startCommandHandler = new StartCommandHandler(telegramBot);

        assertTrue(startCommandHandler.handleCommand(updateWrapper));
    }
}
