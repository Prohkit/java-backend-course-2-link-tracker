package edu.java.bot.updatewrapper;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UpdateWrapperTest {

    private static final String TEXT = "/track https://stackoverflow.com/";

    private UpdateWrapper updateWrapper;

    @Mock
    private Update update;

    @Mock
    private Chat chat;

    @Mock
    private Message message;

    @Test
    void getChatId() {
        Long id = 1L;
        Mockito.when(chat.id()).thenReturn(id);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(update.message()).thenReturn(message);
        updateWrapper = new UpdateWrapper(update);

        Long expected = 1L;

        Long chatId = updateWrapper.getChatId();

        Assertions.assertEquals(chatId, expected);
    }

    @Test
    void getMessageText() {
        Mockito.when(message.text()).thenReturn(TEXT);
        Mockito.when(update.message()).thenReturn(message);
        updateWrapper = new UpdateWrapper(update);

        String expected = "/track https://stackoverflow.com/";

        String messageText = updateWrapper.getMessageText();

        Assertions.assertEquals(messageText, expected);
    }

    @Test
    void getCommand() {
        Mockito.when(message.text()).thenReturn(TEXT);
        Mockito.when(update.message()).thenReturn(message);
        updateWrapper = new UpdateWrapper(update);

        String expected = "/track";

        String command = updateWrapper.getCommand();

        Assertions.assertEquals(command, expected);
    }

    @Test
    void getURLFromMessage() {
        Mockito.when(message.text()).thenReturn(TEXT);
        Mockito.when(update.message()).thenReturn(message);
        updateWrapper = new UpdateWrapper(update);

        String expected = "https://stackoverflow.com/";

        String urlFromMessage = updateWrapper.getURLFromMessage();

        Assertions.assertEquals(urlFromMessage, expected);
    }

    @Test
    void containsUrlInMessage() {
        Mockito.when(message.text()).thenReturn(TEXT);
        Mockito.when(update.message()).thenReturn(message);
        updateWrapper = new UpdateWrapper(update);

        assertTrue(updateWrapper.containsUrlInMessage());
    }
}
