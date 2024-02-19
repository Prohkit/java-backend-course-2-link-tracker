package edu.java.bot.commandhandler;

import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartCommandHandler extends CommandHandler {
    public StartCommandHandler(SendMessageService messageService) {
        this.messageService = messageService;
        command = "/start";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(command)) {
            messageService.sendMessage(update, "Зарегистрирован новый пользователь");
            log.info("Запись нового пользователя в БД");
            return true;
        }
        return false;
    }
}
