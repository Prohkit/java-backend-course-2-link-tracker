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
class TrackCommandHandlerTest {

    private final static String COMMAND = "/track";

    private final static String URL_FROM_MESSAGE = "https://stackoverflow.com/";

    private final static boolean CONTAINS_URL_IN_MESSAGE = true;

    @Mock
    private static UpdateWrapper updateWrapper;

    @Test
    void handleCommandWhenLinkIsAlreadyInDB() {
        Long id = 1L;
        Mockito.when(updateWrapper.getChatId()).thenReturn(id);
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        TelegramBot telegramBot = new TelegramBot("token");
        TempDB tempDB = new TempDB();
        TrackCommandHandler trackCommandHandler = new TrackCommandHandler(tempDB, telegramBot);

        tempDB.addResourceToDB("https://stackoverflow.com/");
        assertFalse(trackCommandHandler.handleCommand(updateWrapper));
        tempDB.removeResourceFromDB("https://stackoverflow.com/");
    }

    @Test
    void handleCommandWhenLinkIsNotInDB() {
        Long id = 1L;
        Mockito.when(updateWrapper.getChatId()).thenReturn(id);
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        Mockito.when(updateWrapper.getURLFromMessage()).thenReturn(URL_FROM_MESSAGE);
        Mockito.when(updateWrapper.containsUrlInMessage()).thenReturn(CONTAINS_URL_IN_MESSAGE);
        TelegramBot telegramBot = new TelegramBot("token");
        TempDB tempDB = new TempDB();
        TrackCommandHandler trackCommandHandler = new TrackCommandHandler(tempDB, telegramBot);

        assertTrue(trackCommandHandler.handleCommand(updateWrapper));
    }

    @Test
    void handleCommandWhenLinkIsInvalid() {
        Long id = 1L;
        String invalidUrl = "asd";
        Mockito.when(updateWrapper.getChatId()).thenReturn(id);
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        Mockito.when(updateWrapper.getURLFromMessage()).thenReturn(invalidUrl);
        Mockito.when(updateWrapper.containsUrlInMessage()).thenReturn(CONTAINS_URL_IN_MESSAGE);
        TelegramBot telegramBot = new TelegramBot("token");
        TempDB tempDB = new TempDB();
        TrackCommandHandler trackCommandHandler = new TrackCommandHandler(tempDB, telegramBot);

        assertFalse(trackCommandHandler.handleCommand(updateWrapper));
    }
}
