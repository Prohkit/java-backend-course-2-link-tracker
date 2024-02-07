package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.TempDB;
import edu.java.bot.updatewrapper.UpdateWrapper;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ListCommandHandler extends CommandHandler {

    private final TempDB tempDB;

    public ListCommandHandler(TempDB tempDB, TelegramBot telegramBot) {
        this.tempDB = tempDB;
        this.telegramBot = telegramBot;
        command = "/list";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(command)) {
            if (!tempDB.isEmpty()) {
                telegramBot.execute(new SendMessage(update.getChatId(), buildListOfTrackedLinks()));
                logger.info("Показываем список используемых ссылок");
                return true;
            }
            telegramBot.execute(new SendMessage(update.getChatId(), "Отслеживаемых ссылок нет"));
            return false;
        }
        return handleNext(update);
    }

    private String buildListOfTrackedLinks() {
        Set<String> resources = tempDB.getAllResources();
        StringBuilder sb = new StringBuilder("Список отслеживаемых ссылок: ")
            .append(System.lineSeparator());
        resources.forEach(resource -> sb.append(resource).append(System.lineSeparator()));
        return new String(sb);
    }
}
