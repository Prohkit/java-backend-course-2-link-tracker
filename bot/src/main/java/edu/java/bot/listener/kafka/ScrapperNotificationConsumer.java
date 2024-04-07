package edu.java.bot.listener.kafka;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.LinkUpdateService;
import edu.java.dto.bot.LinkUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperNotificationConsumer {

    private final ApplicationConfig applicationConfig;

    private final LinkUpdateService linkUpdateService;

    private final KafkaTemplate<Long, LinkUpdate> kafkaTemplate;

    @KafkaListener(groupId = "scrapper.updates.listeners",
                   topics = "${app.kafka-configuration-properties.updates-topic.name}",
                   containerFactory = "kafkaListenerContainerFactory")
    public void listen(LinkUpdate linkUpdate) {
        try {
            linkUpdateService.sendNotification(linkUpdate);
        } catch (Exception e) {
            kafkaTemplate.send(
                applicationConfig.kafkaConfigurationProperties().updatesTopic().name() + "_dlq",
                linkUpdate.getId(),
                linkUpdate
            );
        }
    }
}
