package edu.java.bot.commandhandler;

import edu.java.bot.TempDB;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import static edu.java.bot.util.Util.isUrlValid;

@Component
@Slf4j
public class TrackCommandHandler extends CommandHandler {

    private static final String COMMAND = "/track";

    private final TempDB tempDB;

    public TrackCommandHandler(TempDB tempDB, SendMessageService messageService) {
        this.tempDB = tempDB;
        this.messageService = messageService;
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(COMMAND)) {
            return handleTrackCommand(update);
        }
        return false;
    }

    private boolean handleTrackCommand(UpdateWrapper update) {
        if (update.containsUrlInMessage()) {
            String url = update.getURLFromMessage();
            if (isUrlValid(url)) {
                return saveIfNotAlreadyTracked(url, update);
            }
        }
        messageService.sendMessage(update, "Неверная ссылка");
        return true;
    }

    private boolean saveIfNotAlreadyTracked(String url, UpdateWrapper update) {
        if (!tempDB.containsResource(url)) {
            tempDB.addResourceToDB(url);
            messageService.sendMessage(update, "Начинаем отслеживать ссылку");
            log.info("Начинаем отслеживать ссылку: " + url);
            return true;
        }
        messageService.sendMessage(update, "Эта ссылка уже отслеживается");
        return true;
    }
}
