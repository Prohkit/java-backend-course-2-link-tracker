package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.updatewrapper.UpdateWrapper;

public class StartCommandHandler extends CommandHandler {
    public StartCommandHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        command = "/start";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getMessageText().equals(command)) {
            telegramBot.execute(new SendMessage(update.getChatId(), "Зарегистрирован новый пользователь"));
            logger.info("Запись нового пользователя в БД");
            return true;
        }
        return handleNext(update);
    }
}
