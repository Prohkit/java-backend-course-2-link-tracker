package edu.java.bot.updatewrapper;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.message.MaybeInaccessibleMessage;
import java.util.Optional;

public class UpdateWrapper {
    private final Update update;

    public UpdateWrapper(Update update) {
        this.update = update;
    }

    public Long getChatId() {
        return Optional.ofNullable(update.message())
            .map(MaybeInaccessibleMessage::chat)
            .map(Chat::id)
            .orElseThrow(IllegalArgumentException::new);
    }

    public String getMessageText() {
        return Optional.ofNullable(update.message())
            .map(Message::text)
            .orElse("");
    }

    public String getCommand() {
        return getMessageText().split(" ")[0];
    }

    public String getURLFromMessage() {
        String messageText = getMessageText();
        return messageText.split(" ")[1];
    }

    public boolean containsUrlInMessage() {
        return getMessageText().contains(" ");
    }
}
