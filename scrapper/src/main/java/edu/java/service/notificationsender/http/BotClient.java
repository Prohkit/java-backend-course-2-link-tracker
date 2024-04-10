package edu.java.service.notificationsender.http;

import edu.java.configuration.RetryConfig;
import edu.java.dto.bot.LinkUpdate;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.exception.handler.ApiErrorResponse;
import edu.java.service.NotificationSendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
public class BotClient implements NotificationSendingService {
    private final WebClient webClient;

    private final RetryConfig retryConfig;

    public BotClient(WebClient webClient, RetryConfig retryConfig) {
        this.webClient = webClient;
        this.retryConfig = retryConfig;
    }

    @Override
    public void send(LinkUpdate linkUpdate) {
        webClient
            .post()
            .uri("/updates")
            .bodyValue(linkUpdate)
            .retrieve()
            .toEntity(LinkResponse.class)
            .retryWhen(retryConfig.createRetryPolicy(RetryConfig.BackOffType.LINEAR))
            .doOnError(WebClientResponseException.class, exception -> {
                ApiErrorResponse apiErrorResponse = exception.getResponseBodyAs(ApiErrorResponse.class);
                log.error("Message: {}", apiErrorResponse.getExceptionMessage());
            })
            .onErrorComplete()
            .block();
    }
}
