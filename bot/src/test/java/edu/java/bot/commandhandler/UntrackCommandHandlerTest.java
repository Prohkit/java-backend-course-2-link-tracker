package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.TempDB;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class UntrackCommandHandlerTest {

    private static UpdateWrapper updateWrapper;

    private static UntrackCommandHandler untrackCommandHandler;

    private static TempDB tempDB;

    @BeforeAll
    static void setUp() {
        Update update = new Update();
        tempDB = new TempDB();
        TelegramBot telegramBot = new TelegramBot("token");
        Long id = 1L;
        Chat chat = new Chat();
        Message message = new Message();
        String text = "/untrack https://stackoverflow.com/";
        setField(chat, "id", id);
        setField(message, "text", text);
        setField(message, "chat", chat);
        setField(update, "message", message);
        updateWrapper = new UpdateWrapper(update);
        untrackCommandHandler = new UntrackCommandHandler(tempDB, telegramBot);
    }

    @Test
    void handleCommandWhenLinkIsAlreadyInDB() {
        tempDB.addResourceToDB("https://stackoverflow.com/");
        assertTrue(untrackCommandHandler.handleCommand(updateWrapper));
        tempDB.removeResourceFromDB("https://stackoverflow.com/");
    }

    @Test
    void handleCommandWhenLinkIsNotInDB() {
        assertFalse(untrackCommandHandler.handleCommand(updateWrapper));
    }
}
