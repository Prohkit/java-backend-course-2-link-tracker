package edu.java.bot.commandhandler;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartCommandHandler extends CommandHandler {

    private static final String COMMAND = "/start";

    private final ScrapperClient client;

    public StartCommandHandler(SendMessageService messageService, ScrapperClient client) {
        this.client = client;
        this.messageService = messageService;
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(COMMAND)) {
            ResponseEntity<?> responseEntity = client.registerChat(update.getChatId());
            if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                messageService.sendMessage(update, "Зарегистрирован новый пользователь");
                log.info("Запись нового пользователя в БД");
            }
            return true;
        }
        return false;
    }
}
