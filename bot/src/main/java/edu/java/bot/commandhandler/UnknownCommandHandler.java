package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.updatewrapper.UpdateWrapper;

public class UnknownCommandHandler extends CommandHandler {
    public UnknownCommandHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        telegramBot.execute(new SendMessage(update.getChatId(), "Неизвестная команда"));
        logger.info("Ничего не делаем");
        return true;
    }
}
