package edu.java.bot.commandhandler;

import edu.java.bot.TempDB;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UntrackCommandHandlerTest {

    private static final String COMMAND = "/untrack";

    private static final String URL_FROM_MESSAGE = "https://stackoverflow.com/";

    private static final boolean CONTAINS_URL_IN_MESSAGE = true;

    @Mock
    private static UpdateWrapper updateWrapper;

    @Mock
    private SendMessageService messageService;

    @Test
    void handleCommandWhenLinkIsAlreadyInDB() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        Mockito.when(updateWrapper.getURLFromMessage()).thenReturn(URL_FROM_MESSAGE);
        Mockito.when(updateWrapper.containsUrlInMessage()).thenReturn(CONTAINS_URL_IN_MESSAGE);
        TempDB tempDB = new TempDB();
        UntrackCommandHandler untrackCommandHandler = new UntrackCommandHandler(tempDB, messageService);

        tempDB.addResourceToDB("https://stackoverflow.com/");
        untrackCommandHandler.handleCommand(updateWrapper);

        Mockito.verify(messageService).sendMessage(updateWrapper, "Прекращаем отслеживание ссылки");
        tempDB.removeResourceFromDB("https://stackoverflow.com/");
    }

    @Test
    void handleCommandWhenLinkIsNotInDB() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        Mockito.when(updateWrapper.getURLFromMessage()).thenReturn(URL_FROM_MESSAGE);
        Mockito.when(updateWrapper.containsUrlInMessage()).thenReturn(CONTAINS_URL_IN_MESSAGE);
        TempDB tempDB = new TempDB();
        UntrackCommandHandler untrackCommandHandler = new UntrackCommandHandler(tempDB, messageService);

        untrackCommandHandler.handleCommand(updateWrapper);
        Mockito.verify(messageService).sendMessage(updateWrapper, "Такая ссылка не отслеживается");
    }
}
