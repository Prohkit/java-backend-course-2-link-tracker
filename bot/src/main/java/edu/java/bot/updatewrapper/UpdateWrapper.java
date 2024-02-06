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
}
