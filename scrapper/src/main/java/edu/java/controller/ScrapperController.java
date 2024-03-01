package edu.java.controller;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ScrapperController {

    @PostMapping("/tg-chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void registerChat(@PathVariable Long id) {
        log.info("Регистрация чата с идентификатором {}", id);
    }

    @DeleteMapping("/tg-chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChat(@PathVariable Long id) {
        log.info("Удаление чата с идентификатором {}", id);
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getAllTrackedLinks(@RequestHeader(name = "Tg-Chat-Id") Long tgChatId) {
        log.info("Получение ссылок по идентификатору чата {}", tgChatId);
        return new ResponseEntity<>(new ListLinksResponse(), HttpStatus.OK);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLinkTracking(
        @RequestHeader(name = "Tg-Chat-Id") Long tgChatId,
        @RequestBody AddLinkRequest linkRequest
    ) {
        log.info("Добавление ссылки {} по идентификатору чата {}", linkRequest.getLink(), tgChatId);
        return new ResponseEntity<>(new LinkResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> removeLinkTracking(
        @RequestHeader(name = "Tg-Chat-Id") Long tgChatId,
        @RequestBody RemoveLinkRequest linkRequest
    ) {
        log.info("Удаление ссылки {} по идентификатору чата {}", linkRequest.getLink(), tgChatId);
        return new ResponseEntity<>(new LinkResponse(), HttpStatus.OK);
    }
}
