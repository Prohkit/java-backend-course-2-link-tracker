package edu.java.service.notificationsender.kafka;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.bot.LinkUpdate;
import edu.java.service.NotificationSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class ScrapperQueueProducer implements NotificationSendingService {

    private final ApplicationConfig applicationConfig;

    private final KafkaTemplate<Long, LinkUpdate> kafkaTemplate;

    @Override
    public void send(LinkUpdate update) {
        kafkaTemplate.send(
            applicationConfig.kafkaConfigurationProperties().updatesTopic().name(),
            update.getId(),
            update
        );
    }
}
