package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.TempDB;
import edu.java.bot.updatewrapper.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UntrackCommandHandler extends CommandHandler {

    private final TempDB tempDB;

    public UntrackCommandHandler(TempDB tempDB, TelegramBot telegramBot) {
        this.tempDB = tempDB;
        this.telegramBot = telegramBot;
        command = "/untrack";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(command)) {
            return handleUntrackCommand(update);
        }
        return handleNext(update);
    }

    private boolean handleUntrackCommand(UpdateWrapper update) {
        if (update.containsUrlInMessage()) {
            String url = update.getURLFromMessage();
            if (tempDB.containsResource(url)) {
                tempDB.removeResourceFromDB(url);
                telegramBot.execute(new SendMessage(update.getChatId(), "Прекращаем отслеживание ссылки"));
                log.info("Прекращаем отслеживание ссылки: " + url);
                return true;
            }
        }
        telegramBot.execute(new SendMessage(update.getChatId(), "Такая ссылка не отслеживается"));
        return false;
    }
}
