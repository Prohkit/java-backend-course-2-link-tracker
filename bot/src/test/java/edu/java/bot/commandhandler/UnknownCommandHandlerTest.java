package edu.java.bot.commandhandler;

import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UnknownCommandHandlerTest {
    @Mock
    private UpdateWrapper updateWrapper;

    @Mock
    private CommandHandlersChain commandHandlersChain;

    @Mock
    private SendMessageService messageService;

    @Mock
    private List<CommandHandler> handlerList;

    @Test
    void handleCommand() {
        commandHandlersChain = new CommandHandlersChain(handlerList, messageService);

        commandHandlersChain.handleCommand(updateWrapper);

        Mockito.verify(messageService).sendMessage(updateWrapper, "Неизвестная команда");
    }
}
