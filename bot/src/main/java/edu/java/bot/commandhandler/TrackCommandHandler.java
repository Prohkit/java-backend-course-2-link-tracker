package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.updatewrapper.UpdateWrapper;

public class TrackCommandHandler extends CommandHandler {
    public TrackCommandHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        command = "/track";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getMessageText().equals(command)) {
            telegramBot.execute(new SendMessage(update.getChatId(), "Начинаем отслеживать ссылку"));
            logger.info("Начинаем отслеживать ссылку");
            return true;
        }
        return handleNext(update);
    }
}
