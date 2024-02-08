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

class ListCommandHandlerTest {

    private static UpdateWrapper updateWrapper;

    private static ListCommandHandler listCommandHandler;

    private static TempDB tempDB;

    @BeforeAll
    static void setUp() {
        Update update = new Update();
        tempDB = new TempDB();
        TelegramBot telegramBot = new TelegramBot("token");
        Long id = 1L;
        Chat chat = new Chat();
        Message message = new Message();
        String text = "/list";
        setField(chat, "id", id);
        setField(message, "text", text);
        setField(message, "chat", chat);
        setField(update, "message", message);
        updateWrapper = new UpdateWrapper(update);
        listCommandHandler = new ListCommandHandler(tempDB, telegramBot);
    }

    @Test
    void handleCommandWhenLinksAreInDB() {
        tempDB.addResourceToDB("https://stackoverflow.com/");
        assertTrue(listCommandHandler.handleCommand(updateWrapper));
        tempDB.removeResourceFromDB("https://stackoverflow.com/");
    }

    @Test
    void handleCommandWhenDBIsEmpty() {
        assertFalse(listCommandHandler.handleCommand(updateWrapper));
    }
}
