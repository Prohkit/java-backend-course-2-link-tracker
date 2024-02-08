package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class HelpCommandHandlerTest {

    private static UpdateWrapper updateWrapper;

    private static HelpCommandHandler helpCommandHandler;

    private static TelegramBot telegramBot;

    @BeforeAll
    static void setUp() {
        Update update = new Update();
        Long id = 1L;
        Chat chat = new Chat();
        Message message = new Message();
        String text = "/track https://stackoverflow.com/";
        setField(chat, "id", id);
        setField(message, "text", text);
        setField(update, "message", message);
        updateWrapper = new UpdateWrapper(update);
        helpCommandHandler = new HelpCommandHandler(telegramBot);
    }

    @Test
    void handleCommand() {
        assertTrue(helpCommandHandler.handleCommand(updateWrapper));
    }
}
