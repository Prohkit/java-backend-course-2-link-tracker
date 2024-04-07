package edu.java.configuration;

import edu.java.dto.bot.LinkUpdate;
import edu.java.service.NotificationSendingService;
import edu.java.service.notificationsender.http.BotClient;
import edu.java.service.notificationsender.kafka.ScrapperQueueProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class NotificationSendingConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
    public NotificationSendingService scrapperQueueProducer(
        ApplicationConfig applicationConfig,
        KafkaTemplate<Long, LinkUpdate> kafkaTemplate
    ) {
        return new ScrapperQueueProducer(applicationConfig, kafkaTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
    public NotificationSendingService botClient(
        @Value("http://localhost:8090") String baseUrl,
        RetryConfig retryConfig
    ) {
        return new BotClient(WebClient.builder().baseUrl(baseUrl).build(), retryConfig);
    }
}
