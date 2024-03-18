package edu.java.controller;

import edu.java.dto.scrapper.request.AddLinkRequest;
import edu.java.dto.scrapper.request.RemoveLinkRequest;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import edu.java.exception.ChatAlreadyRegisteredException;
import edu.java.exception.ChatDoesNotExistException;
import edu.java.exception.LinkHasAlreadyBeenAddedException;
import edu.java.exception.LinkNotFoundException;
import edu.java.service.LinkService;
import edu.java.service.TelegramChatService;
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
@SuppressWarnings("MultipleStringLiterals")
public class ScrapperController {

    private final LinkService linkService;

    private final TelegramChatService telegramChatService;

    public ScrapperController(LinkService linkService, TelegramChatService telegramChatService) {
        this.linkService = linkService;
        this.telegramChatService = telegramChatService;
    }

    @PostMapping("/tg-chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void registerChat(@PathVariable Long id) {
        boolean isChatExist = telegramChatService.isChatExists(id);
        if (isChatExist) {
            throw new ChatAlreadyRegisteredException("Чат уже зарегистрирован");
        }
        log.info("Регистрация чата с идентификатором {}", id);
        telegramChatService.register(id);
    }

    @DeleteMapping("/tg-chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChat(@PathVariable Long id) {
        boolean isChatExist = telegramChatService.isChatExists(id);
        if (!isChatExist) {
            throw new ChatDoesNotExistException("Чат не найден");
        }
        log.info("Удаление чата с идентификатором {}", id);
        telegramChatService.unregister(id);
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getAllTrackedLinks(
        @RequestHeader(name = "Tg-Chat-Id") Long telegramChatId
    ) {
        boolean isChatExist = telegramChatService.isChatExists(telegramChatId);
        if (!isChatExist) {
            throw new ChatDoesNotExistException("Чат не найден");
        }
        log.info("Получение ссылок по идентификатору чата {}", telegramChatId);
        ListLinksResponse listLinksResponse = linkService.listAllLinks(telegramChatId);
        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLinkTracking(
        @RequestHeader(name = "Tg-Chat-Id") Long telegramChatId,
        @RequestBody AddLinkRequest linkRequest
    ) {
        boolean isChatExist = telegramChatService.isChatExists(telegramChatId);
        if (!isChatExist) {
            throw new ChatDoesNotExistException("Чат не найден");
        }
        boolean hasLinkAlreadyBeenAdded = linkService.isLinkConnectedToChat(telegramChatId, linkRequest.getLink());
        if (hasLinkAlreadyBeenAdded) {
            throw new LinkHasAlreadyBeenAddedException("Ссылка уже добавлена");
        }
        log.info("Добавление ссылки {} по идентификатору чата {}", linkRequest.getLink(), telegramChatId);
        LinkResponse linkResponse = linkService.addLink(telegramChatId, linkRequest.getLink());
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> removeLinkTracking(
        @RequestHeader(name = "Tg-Chat-Id") Long telegramChatId,
        @RequestBody RemoveLinkRequest linkRequest
    ) {
        boolean isChatExist = telegramChatService.isChatExists(telegramChatId);
        if (!isChatExist) {
            throw new ChatDoesNotExistException("Чат не найден");
        }
        boolean isLinkExist = linkService.isLinkConnectedToChat(telegramChatId, linkRequest.getLink());
        if (!isLinkExist) {
            throw new LinkNotFoundException("Ссылка не найдена");
        }
        log.info("Удаление ссылки {} по идентификатору чата {}", linkRequest.getLink(), telegramChatId);
        LinkResponse linkResponse = linkService.removeLink(telegramChatId, linkRequest.getLink());
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }
}
