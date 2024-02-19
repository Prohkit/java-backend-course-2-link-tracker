package edu.java.bot.commandhandler;

import edu.java.bot.TempDB;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListCommandHandler extends CommandHandler {

    private final TempDB tempDB;

    public ListCommandHandler(TempDB tempDB, SendMessageService messageService) {
        this.tempDB = tempDB;
        this.messageService = messageService;
        command = "/list";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(command)) {
            if (!tempDB.isEmpty()) {
                messageService.sendMessage(update, buildListOfTrackedLinks());
                log.info("Показываем список используемых ссылок");
                return true;
            }
            messageService.sendMessage(update, "Отслеживаемых ссылок нет");
            return true;
        }
        return false;
    }

    private String buildListOfTrackedLinks() {
        Set<String> resources = tempDB.getAllResources();
        StringBuilder sb = new StringBuilder("Список отслеживаемых ссылок: ")
            .append(System.lineSeparator());
        resources.forEach(resource -> sb.append(resource).append(System.lineSeparator()));
        return new String(sb);
    }
}
