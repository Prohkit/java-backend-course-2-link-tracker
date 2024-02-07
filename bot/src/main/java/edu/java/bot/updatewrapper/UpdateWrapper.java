package edu.java.bot.updatewrapper;

import com.pengrad.telegrambot.model.Update;

public class UpdateWrapper {
    private final Update update;

    public UpdateWrapper(Update update) {
        this.update = update;
    }

    public Long getChatId() {
        return update.message().chat().id();
    }

    public String getMessageText() {
        return update.message().text();
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
