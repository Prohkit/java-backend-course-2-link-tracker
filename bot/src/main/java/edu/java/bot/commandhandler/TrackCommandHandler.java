package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.TempDB;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.springframework.stereotype.Component;
import static edu.java.bot.util.Util.isUrlValid;

@Component
public class TrackCommandHandler extends CommandHandler {

    private final TempDB tempDB;

    public TrackCommandHandler(TempDB tempDB, TelegramBot telegramBot) {
        this.tempDB = tempDB;
        this.telegramBot = telegramBot;
        command = "/track";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(command)) {
            return handleTrackCommand(update);
        }
        return handleNext(update);
    }

    private boolean handleTrackCommand(UpdateWrapper update) {
        if (update.containsUrlInMessage()) {
            String url = update.getURLFromMessage();
            if (isUrlValid(url)) {
                return saveIfNotAlreadyTracked(url, update);
            }
        }
        telegramBot.execute(new SendMessage(update.getChatId(), "Неверная ссылка"));
        return false;
    }

    private boolean saveIfNotAlreadyTracked(String url, UpdateWrapper update) {
        if (!tempDB.containsResource(url)) {
            tempDB.addResourceToDB(url);
            telegramBot.execute(new SendMessage(update.getChatId(), "Начинаем отслеживать ссылку"));
            logger.info("Начинаем отслеживать ссылку: " + url);
            return true;
        }
        telegramBot.execute(new SendMessage(update.getChatId(), "Эта ссылка уже отслеживается"));
        return false;
    }
}
