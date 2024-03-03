package edu.java.bot.client;

import edu.java.bot.exception.handler.ApiErrorResponse;
import edu.java.bot.service.SendMessageService;
import edu.java.dto.scrapper.request.AddLinkRequest;
import edu.java.dto.scrapper.request.RemoveLinkRequest;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@SuppressWarnings("MultipleStringLiterals")
public class ScrapperClient {
    private final WebClient webClient;

    private final SendMessageService service;

    public ScrapperClient(WebClient webClient, SendMessageService service) {
        this.webClient = webClient;
        this.service = service;
    }

    public ResponseEntity<Void> registerChat(Long id) {
        return webClient
            .post()
            .uri("/tg-chat/" + id)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .handle(((apiErrorResponse, throwableSynchronousSink) -> {
                        service.sendApiError(id, apiErrorResponse);
                        log.error("ChatId: {} Message: {}", id, apiErrorResponse.getExceptionMessage());
                    }
                    ))
            )
            .toEntity(Void.class)
            .block();
    }

    public void deleteChat(Long id) {
        webClient
            .delete()
            .uri("/tg-chat/" + id)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .handle(((apiErrorResponse, throwableSynchronousSink) -> {
                        service.sendApiError(id, apiErrorResponse);
                        log.error("ChatId: {} Message: {}", id, apiErrorResponse.getExceptionMessage());
                    }
                    ))
            )
            .toEntity(Void.class)
            .block();
    }

    public ResponseEntity<ListLinksResponse> getAllTrackedLinks(Long tgChatId) {
        return webClient
            .get()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .handle(((apiErrorResponse, throwableSynchronousSink) -> {
                        service.sendApiError(tgChatId, apiErrorResponse);
                        log.error("ChatId: {} Message: {}", tgChatId, apiErrorResponse.getExceptionMessage());
                    }
                    ))
            )
            .toEntity(ListLinksResponse.class)
            .block();
    }

    public ResponseEntity<LinkResponse> addLinkTracking(Long tgChatId, AddLinkRequest linkRequest) {
        return webClient
            .post()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .bodyValue(linkRequest)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .handle(((apiErrorResponse, throwableSynchronousSink) -> {
                        service.sendApiError(tgChatId, apiErrorResponse);
                        log.error("ChatId: {} Message: {}", tgChatId, apiErrorResponse.getExceptionMessage());
                    }
                    ))
            )
            .toEntity(LinkResponse.class)
            .block();
    }

    public ResponseEntity<Void> removeLinkTracking(Long tgChatId, RemoveLinkRequest linkRequest) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .body(Mono.just(linkRequest), RemoveLinkRequest.class)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .handle(((apiErrorResponse, throwableSynchronousSink) -> {
                        service.sendApiError(tgChatId, apiErrorResponse);
                        log.error("ChatId: {} Message: {}", tgChatId, apiErrorResponse.getExceptionMessage());
                    }
                    ))
            )
            .toEntity(Void.class)
            .block();
    }
}
