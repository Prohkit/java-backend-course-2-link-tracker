package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class UnknownCommandHandlerTest {

    private static UpdateWrapper updateWrapper;

    private static UnknownCommandHandler unknownCommandHandler;

    @BeforeAll
    static void setUp() {
        Update update = new Update();
        TelegramBot telegramBot = new TelegramBot("Token");
        Long id = 1L;
        Chat chat = new Chat();
        Message message = new Message();
        String text = "/unknown command";
        setField(chat, "id", id);
        setField(message, "text", text);
        setField(message, "chat", chat);
        setField(update, "message", message);
        updateWrapper = new UpdateWrapper(update);
        unknownCommandHandler = new UnknownCommandHandler(telegramBot);
    }

    @Test
    void handleCommand() {
        assertTrue(unknownCommandHandler.handleCommand(updateWrapper));
    }
}
