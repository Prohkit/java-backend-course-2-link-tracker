package edu.java.bot.controller;

import edu.java.dto.bot.LinkUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BotController {
    @PostMapping(consumes = "application/json", value = "/updates")
    @ResponseStatus(HttpStatus.OK)
    public void sendResponse(@RequestBody LinkUpdate linkUpdate) {
        log.info("Отправка обновления на обработку");
    }
}
