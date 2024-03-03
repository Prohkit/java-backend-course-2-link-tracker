package edu.java.client;

import edu.java.dto.bot.LinkUpdate;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.exception.handler.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class BotClient {
    private final WebClient webClient;

    public BotClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void sendUpdate(LinkUpdate linkUpdate) {
        webClient
            .post()
            .uri("/updates")
            .bodyValue(linkUpdate)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .handle(((apiErrorResponse, throwableSynchronousSink) -> {
                        log.error("Message: {}", apiErrorResponse.getExceptionMessage());
                    }
                    ))
            )
            .toEntity(LinkResponse.class)
            .block();
    }
}
