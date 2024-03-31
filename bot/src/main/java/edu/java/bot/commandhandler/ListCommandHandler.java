package edu.java.bot.commandhandler;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import edu.java.dto.scrapper.response.ListLinksResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListCommandHandler extends CommandHandler {

    private static final String COMMAND = "/list";

    private final ScrapperClient client;

    public ListCommandHandler(ScrapperClient client, SendMessageService messageService) {
        this.client = client;
        this.messageService = messageService;
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(COMMAND)) {
            ResponseEntity<ListLinksResponse> responseEntity = client.getAllTrackedLinks(update.getChatId());
            if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                ListLinksResponse listLinksResponse = responseEntity.getBody();
                messageService.sendMessage(update, buildListOfTrackedLinks(listLinksResponse));
                log.info("Показываем список используемых ссылок");
                return true;
            }
            messageService.sendMessage(update, "Сервис временно недоступен.");
            return true;
        }
        return false;
    }

    private String buildListOfTrackedLinks(ListLinksResponse listLinksResponse) {
        if (listLinksResponse.getSize() == 0) {
            return "Нет отслеживаемых ссылок.";
        }
        List<String> resources =
            listLinksResponse.getLinks().stream().map(linkResponse -> linkResponse.getUrl().toString()).toList();
        StringBuilder sb = new StringBuilder("Количество отслеживаемых ссылок: ")
            .append(listLinksResponse.getSize())
            .append(System.lineSeparator())
            .append("Список отслеживаемых ссылок: ")
            .append(System.lineSeparator());
        resources.forEach(resource -> sb.append(resource).append(System.lineSeparator()));
        return new String(sb);
    }
}
