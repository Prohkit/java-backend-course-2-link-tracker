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

class StartCommandHandlerTest {

    private static UpdateWrapper updateWrapper;

    private static StartCommandHandler startCommandHandler;

    @BeforeAll
    static void setUp() {
        Update update = new Update();
        TelegramBot telegramBot = new TelegramBot("Token");
        Long id = 1L;
        Chat chat = new Chat();
        Message message = new Message();
        String text = "/start";
        setField(chat, "id", id);
        setField(message, "text", text);
        setField(message, "chat", chat);
        setField(update, "message", message);
        updateWrapper = new UpdateWrapper(update);
        startCommandHandler = new StartCommandHandler(telegramBot);
    }

    @Test
    void handleCommand() {
        assertTrue(startCommandHandler.handleCommand(updateWrapper));
    }
}
