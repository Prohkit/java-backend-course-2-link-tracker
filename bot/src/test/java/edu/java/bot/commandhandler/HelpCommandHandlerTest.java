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
class HelpCommandHandlerTest {
    private final static String COMMAND = "/help";

    @Mock
    private UpdateWrapper updateWrapper;

    @Test
    void handleCommand() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        TelegramBot telegramBot = new TelegramBot("token");
        HelpCommandHandler helpCommandHandler = new HelpCommandHandler(telegramBot);

        assertTrue(helpCommandHandler.handleCommand(updateWrapper));
    }
}
