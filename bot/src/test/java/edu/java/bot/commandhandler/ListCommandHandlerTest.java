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
class ListCommandHandlerTest {
    private static final String COMMAND = "/list";

    @Mock
    private UpdateWrapper updateWrapper;

    @Mock
    private SendMessageService messageService;

    @Test
    void handleCommandWhenLinksAreInDB() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        TempDB tempDB = new TempDB();
        ListCommandHandler listCommandHandler = new ListCommandHandler(tempDB, messageService);

        tempDB.addResourceToDB("https://stackoverflow.com/");
        listCommandHandler.handleCommand(updateWrapper);

        Mockito.verify(messageService)
            .sendMessage(updateWrapper, "Список отслеживаемых ссылок: \r\nhttps://stackoverflow.com/\r\n");
        tempDB.removeResourceFromDB("https://stackoverflow.com/");
    }

    @Test
    void handleCommandWhenDBIsEmpty() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        TempDB tempDB = new TempDB();
        ListCommandHandler listCommandHandler = new ListCommandHandler(tempDB, messageService);

        listCommandHandler.handleCommand(updateWrapper);

        Mockito.verify(messageService).sendMessage(updateWrapper, "Отслеживаемых ссылок нет");
    }
}
