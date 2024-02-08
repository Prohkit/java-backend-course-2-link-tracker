package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.TempDB;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class TrackCommandHandlerTest {

    private static UpdateWrapper updateWrapper;

    private static Update update;

    private static TrackCommandHandler trackCommandHandler;

    private static TempDB tempDB;

    @BeforeAll
    static void setUp() {
        update = new Update();
        tempDB = new TempDB();
        TelegramBot telegramBot = new TelegramBot("token");
        Long id = 1L;
        Chat chat = new Chat();
        Message message = new Message();
        String text = "/track https://stackoverflow.com/";
        setField(chat, "id", id);
        setField(message, "text", text);
        setField(message, "chat", chat);
        setField(update, "message", message);
        updateWrapper = new UpdateWrapper(update);
        trackCommandHandler = new TrackCommandHandler(tempDB, telegramBot);
    }

    @Test
    void handleCommandWhenLinkIsAlreadyInDB() {
        tempDB.addResourceToDB("https://stackoverflow.com/");
        assertFalse(trackCommandHandler.handleCommand(updateWrapper));
        tempDB.removeResourceFromDB("https://stackoverflow.com/");
    }

    @Test
    void handleCommandWhenLinkIsNotInDB() {
        assertTrue(trackCommandHandler.handleCommand(updateWrapper));
    }

    @Test
    void handleCommandWhenLinkIsInvalid() {
        String text = "/track asd";
        Message message = update.message();
        setField(message, "text", text);
        setField(update, "message", message);

        assertFalse(trackCommandHandler.handleCommand(updateWrapper));

        text = "/track https://stackoverflow.com/";
        setField(message, "text", text);
        setField(update, "message", message);
    }
}
