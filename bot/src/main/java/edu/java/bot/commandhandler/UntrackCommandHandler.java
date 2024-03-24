package edu.java.bot.commandhandler;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import edu.java.dto.scrapper.request.RemoveLinkRequest;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UntrackCommandHandler extends CommandHandler {

    private static final String COMMAND = "/untrack";

    private final ScrapperClient client;

    public UntrackCommandHandler(ScrapperClient client, SendMessageService messageService) {
        this.client = client;
        this.messageService = messageService;
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(COMMAND)) {
            return handleUntrackCommand(update);
        }
        return false;
    }

    private boolean handleUntrackCommand(UpdateWrapper update) {
        if (update.containsUrlInMessage()) {
            String url = update.getURLFromMessage();
            ResponseEntity<?> responseEntity =
                client.removeLinkTracking(update.getChatId(), new RemoveLinkRequest(URI.create(url)));
            if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                messageService.sendMessage(update, "Прекращаем отслеживание ссылки");
                log.info("Прекращаем отслеживание ссылки: " + url);
            }
        }
        return true;
    }
}
