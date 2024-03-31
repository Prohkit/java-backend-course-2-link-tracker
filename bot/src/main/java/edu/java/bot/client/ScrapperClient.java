package edu.java.bot.client;

import edu.java.bot.configuration.RetryConfig;
import edu.java.bot.exception.handler.ApiErrorResponse;
import edu.java.bot.service.SendMessageService;
import edu.java.dto.scrapper.request.AddLinkRequest;
import edu.java.dto.scrapper.request.RemoveLinkRequest;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@SuppressWarnings("MultipleStringLiterals")
public class ScrapperClient {
    private final WebClient webClient;

    private final SendMessageService service;

    private final RetryConfig retryConfig;

    public ScrapperClient(WebClient webClient, SendMessageService service, RetryConfig retryConfig) {
        this.webClient = webClient;
        this.service = service;
        this.retryConfig = retryConfig;
    }

    public ResponseEntity<Void> registerChat(Long id) {
        return webClient
            .post()
            .uri("/tg-chat/" + id)
            .retrieve()
            .toEntity(Void.class)
            .retryWhen(retryConfig.createRetryPolicy(RetryConfig.BackOffType.LINEAR))
            .doOnError(WebClientResponseException.class, exception -> handleError(id, exception))
            .onErrorComplete()
            .block();
    }

    public void deleteChat(Long id) {
        webClient
            .delete()
            .uri("/tg-chat/" + id)
            .retrieve()
            .toEntity(Void.class)
            .retryWhen(retryConfig.createRetryPolicy(RetryConfig.BackOffType.LINEAR))
            .doOnError(WebClientResponseException.class, exception -> handleError(id, exception))
            .onErrorComplete()
            .block();
    }

    public ResponseEntity<ListLinksResponse> getAllTrackedLinks(Long tgChatId) {
        return webClient
            .get()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .retrieve()
            .toEntity(ListLinksResponse.class)
            .retryWhen(retryConfig.createRetryPolicy(RetryConfig.BackOffType.LINEAR))
            .doOnError(WebClientResponseException.class, exception -> handleError(tgChatId, exception))
            .onErrorComplete()
            .block();
    }

    public ResponseEntity<LinkResponse> addLinkTracking(Long tgChatId, AddLinkRequest linkRequest) {
        return webClient
            .post()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .bodyValue(linkRequest)
            .retrieve()
            .toEntity(LinkResponse.class)
            .retryWhen(retryConfig.createRetryPolicy(RetryConfig.BackOffType.LINEAR))
            .doOnError(WebClientResponseException.class, exception -> handleError(tgChatId, exception))
            .onErrorComplete()
            .block();
    }

    public ResponseEntity<Void> removeLinkTracking(Long tgChatId, RemoveLinkRequest linkRequest) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .body(Mono.just(linkRequest), RemoveLinkRequest.class)
            .retrieve()
            .toEntity(Void.class)
            .retryWhen(retryConfig.createRetryPolicy(RetryConfig.BackOffType.LINEAR))
            .doOnError(WebClientResponseException.class, exception -> handleError(tgChatId, exception))
            .onErrorComplete()
            .block();
    }

    private void handleError(Long id, WebClientResponseException exception) {
        ApiErrorResponse apiErrorResponse = exception.getResponseBodyAs(ApiErrorResponse.class);
        service.sendApiError(id, apiErrorResponse);
        log.error("ChatId: {} Message: {}", id, apiErrorResponse.getExceptionMessage());
    }
}
