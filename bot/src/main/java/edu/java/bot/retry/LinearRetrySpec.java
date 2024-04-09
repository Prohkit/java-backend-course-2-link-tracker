package edu.java.bot.retry;

import java.time.Duration;
import java.util.function.Predicate;
import org.reactivestreams.Publisher;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class LinearRetrySpec extends Retry {

    private final int maxAttempts;
    private Duration delay;
    public final Predicate<Throwable> errorFilter;
    private final Integer multiplier;
    private final Duration maxDelay;

    public LinearRetrySpec(
        int maxAttempts,
        Duration delay,
        Predicate<? super Throwable> aThrowablePredicate,
        Integer multiplier,
        Duration maxDelay
    ) {
        this.maxAttempts = maxAttempts;
        this.delay = delay;
        this.errorFilter = aThrowablePredicate::test;
        this.multiplier = multiplier;
        this.maxDelay = maxDelay;
    }

    @Override
    public Publisher<?> generateCompanion(Flux<RetrySignal> retrySignals) {
        return retrySignals.flatMap(this::getRetry);
    }

    private Mono<Long> getRetry(RetrySignal rs) {
        Throwable currentFailure = rs.failure();
        if (!errorFilter.test(currentFailure)) {
            return Mono.error(currentFailure);
        }

        if (rs.totalRetries() < maxAttempts) {
            delay = delay.multipliedBy(multiplier);
            boolean isDelayMoreThanMaxDelay = delay.compareTo(maxDelay) > 0;
            if (isDelayMoreThanMaxDelay) {
                return Mono.delay(maxDelay).thenReturn(rs.totalRetries());
            }
            return Mono.delay(delay).thenReturn(rs.totalRetries());
        } else {
            throw Exceptions.propagate(rs.failure());
        }
    }
}
