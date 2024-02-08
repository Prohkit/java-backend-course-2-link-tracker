package edu.java.bot.updatewrapper;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class UpdateWrapperTest {
    @Mock
    private static UpdateWrapper updateWrapper;

    @Mock
    private static Update update;

    @BeforeAll
    static void setUp() {
        update = new Update();
        Long id = 1L;
        Chat chat = new Chat();
        Message message = new Message();
        String text = "/track https://stackoverflow.com/";
        setField(chat, "id", id);
        setField(message, "chat", chat);
        setField(message, "text", text);
        setField(update, "message", message);
        updateWrapper = new UpdateWrapper(update);
    }

    @Test
    void getChatId() {
        Long expected = 1L;

        Long chatId = updateWrapper.getChatId();

        Assertions.assertEquals(chatId, expected);
    }

    @Test
    void getMessageText() {
        String expected = "/track https://stackoverflow.com/";

        String messageText = updateWrapper.getMessageText();

        Assertions.assertEquals(messageText, expected);
    }

    @Test
    void getCommand() {
        String expected = "/track";

        String command = updateWrapper.getCommand();

        Assertions.assertEquals(command, expected);
    }

    @Test
    void getURLFromMessage() {
        String expected = "https://stackoverflow.com/";

        String urlFromMessage = updateWrapper.getURLFromMessage();

        Assertions.assertEquals(urlFromMessage, expected);
    }

    @Test
    void containsUrlInMessage() {
        assertTrue(updateWrapper.containsUrlInMessage());
    }
}
