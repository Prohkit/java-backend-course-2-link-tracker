package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.updatewrapper.UpdateWrapper;

public class UntrackCommandHandler extends CommandHandler {
    public UntrackCommandHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        command = "/untrack";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getMessageText().equals(command)) {
            telegramBot.execute(new SendMessage(update.getChatId(), "Прекращаем отслеживание ссылки"));
            logger.info("Прекращаем отслеживание ссылки");
            return true;
        }
        return handleNext(update);
    }
}
