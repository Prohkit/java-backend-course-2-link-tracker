package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.TempDB;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ListCommandHandlerTest {
    private final static String COMMAND = "/list";

    @Mock
    private UpdateWrapper updateWrapper;

    @Test
    void handleCommandWhenLinksAreInDB() {
        Long id = 1L;
        Mockito.when(updateWrapper.getChatId()).thenReturn(id);
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        TelegramBot telegramBot = new TelegramBot("token");
        TempDB tempDB = new TempDB();
        ListCommandHandler listCommandHandler = new ListCommandHandler(tempDB, telegramBot);

        tempDB.addResourceToDB("https://stackoverflow.com/");
        assertTrue(listCommandHandler.handleCommand(updateWrapper));
        tempDB.removeResourceFromDB("https://stackoverflow.com/");
    }

    @Test
    void handleCommandWhenDBIsEmpty() {
        Long id = 1L;
        Mockito.when(updateWrapper.getChatId()).thenReturn(id);
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        TelegramBot telegramBot = new TelegramBot("token");
        TempDB tempDB = new TempDB();
        ListCommandHandler listCommandHandler = new ListCommandHandler(tempDB, telegramBot);

        assertFalse(listCommandHandler.handleCommand(updateWrapper));
    }
}
