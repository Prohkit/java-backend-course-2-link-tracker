package edu.java.bot.commandhandler;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import edu.java.dto.scrapper.request.AddLinkRequest;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import static edu.java.bot.util.Util.isUrlValid;

@Component
@Slf4j
public class TrackCommandHandler extends CommandHandler {

    private static final String COMMAND = "/track";

    private final ScrapperClient client;

    public TrackCommandHandler(ScrapperClient client, SendMessageService messageService) {
        this.client = client;
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
        ResponseEntity<?> responseEntity =
            client.addLinkTracking(update.getChatId(), new AddLinkRequest(URI.create(url)));
        if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            messageService.sendMessage(update, "Начинаем отслеживать ссылку");
            log.info("Начинаем отслеживать ссылку: " + url);
        }
        return true;
    }
}
