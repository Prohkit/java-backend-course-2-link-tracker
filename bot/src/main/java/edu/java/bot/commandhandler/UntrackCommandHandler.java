package edu.java.bot.commandhandler;

import edu.java.bot.TempDB;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UntrackCommandHandler extends CommandHandler {

    private final TempDB tempDB;

    public UntrackCommandHandler(TempDB tempDB, SendMessageService messageService) {
        this.tempDB = tempDB;
        this.messageService = messageService;
        command = "/untrack";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(command)) {
            return handleUntrackCommand(update);
        }
        return false;
    }

    private boolean handleUntrackCommand(UpdateWrapper update) {
        if (update.containsUrlInMessage()) {
            String url = update.getURLFromMessage();
            if (tempDB.containsResource(url)) {
                tempDB.removeResourceFromDB(url);
                messageService.sendMessage(update, "Прекращаем отслеживание ссылки");
                log.info("Прекращаем отслеживание ссылки: " + url);
                return true;
            }
        }
        messageService.sendMessage(update, "Такая ссылка не отслеживается");
        return true;
    }
}
