package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UnknownCommandHandlerTest {

    @Mock
    private static UpdateWrapper updateWrapper;

    @Test
    void handleCommand() {
        TelegramBot telegramBot = new TelegramBot("token");
        UnknownCommandHandler unknownCommandHandler = new UnknownCommandHandler(telegramBot);

        assertTrue(unknownCommandHandler.handleCommand(updateWrapper));
    }
}
