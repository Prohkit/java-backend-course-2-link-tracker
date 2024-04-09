package edu.java.bot.configuration;

import edu.java.bot.retry.LinearRetrySpec;
import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

@Configuration
@EnableCaching
@Data
public class RetryConfig {

    @Value("${retry.max-attempts}")
    private Integer maxAttempts;
    @Value("${retry.initial-delay}")
    private Integer initialDelay;
    @Value("${retry.max-delay}")
    private Integer maxDelay;
    @Value("#{'${retry.status-codes}'.split(',')}")
    private List<Integer> statusCodes;
    @Value("${retry.linear-multiplier}")
    private Integer linearMultiplier;

    public Retry createRetryPolicy(
        BackOffType backOffType
    ) {
        Predicate<Throwable> retryOnStatusCodes = e ->
            e instanceof WebClientResponseException webClientResponseException
                && statusCodes.contains((webClientResponseException).getStatusCode().value());

        return switch (backOffType) {
            case CONSTANT -> Retry.fixedDelay(maxAttempts, Duration.ofSeconds(initialDelay)).filter(retryOnStatusCodes);
            case LINEAR -> new LinearRetrySpec(maxAttempts, Duration.ofSeconds(initialDelay), retryOnStatusCodes,
                linearMultiplier, Duration.ofSeconds(maxDelay)
            );
            case EXPONENTIAL -> Retry.backoff(maxAttempts, Duration.ofSeconds(initialDelay))
                .maxBackoff(Duration.ofSeconds(maxDelay))
                .filter(retryOnStatusCodes);
        };
    }

    public enum BackOffType {
        CONSTANT, LINEAR, EXPONENTIAL
    }
}
