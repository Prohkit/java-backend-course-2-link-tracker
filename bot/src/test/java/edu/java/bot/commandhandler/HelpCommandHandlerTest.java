package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.model.request.ParseMode;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HelpCommandHandlerTest {
    private static final String COMMAND = "/help";

    private static final String text = "/start -- зарегистрировать пользователя\n" +
        "/help -- вывести окно с командами\n" +
        "/track -- начать отслеживание ссылки\n" +
        "/untrack -- прекратить отслеживание ссылки\n" +
        "/list -- показать список отслеживаемых ссылок";

    @Mock
    private UpdateWrapper updateWrapper;

    @Mock
    private SendMessageService messageService;

    @Test
    void handleCommand() {
        Mockito.when(updateWrapper.getCommand()).thenReturn(COMMAND);
        HelpCommandHandler helpCommandHandler = new HelpCommandHandler(messageService);

        helpCommandHandler.handleCommand(updateWrapper);

        Mockito.verify(messageService).sendMessage(updateWrapper, text, ParseMode.Markdown);
    }
}
