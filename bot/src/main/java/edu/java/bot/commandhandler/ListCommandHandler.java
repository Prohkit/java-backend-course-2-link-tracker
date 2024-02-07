package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.updatewrapper.UpdateWrapper;

public class ListCommandHandler extends CommandHandler {
    public ListCommandHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        command = "/list";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getMessageText().equals(command)) {
            telegramBot.execute(new SendMessage(update.getChatId(), "Список отслеживаемых ссылок: "));
            logger.info("Показываем список используемых ссылок");
            return true;
        }
        return handleNext(update);
    }
}
