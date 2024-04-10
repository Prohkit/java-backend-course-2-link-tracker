package edu.java.configuration;

import edu.java.dto.bot.LinkUpdate;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfiguration {

    private final ApplicationConfig applicationConfig;

    public KafkaConfiguration(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder
            .name(applicationConfig.kafkaConfigurationProperties().updatesTopic().name())
            .partitions(applicationConfig.kafkaConfigurationProperties().updatesTopic().partitions())
            .replicas(applicationConfig.kafkaConfigurationProperties().updatesTopic().replicas())
            .build();
    }

    @Bean
    public NewTopic dlqTopic() {
        return TopicBuilder
            .name(applicationConfig.kafkaConfigurationProperties().updatesTopic().name() + "_dlq")
            .partitions(applicationConfig.kafkaConfigurationProperties().updatesTopic().partitions())
            .replicas(applicationConfig.kafkaConfigurationProperties().updatesTopic().replicas())
            .build();
    }

    @Bean
    public ProducerFactory<Long, LinkUpdate> producerFactory() {
        return new DefaultKafkaProducerFactory<>(senderProps());
    }

    private Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            applicationConfig.kafkaConfigurationProperties().bootstrapServers()
        );
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return props;
    }

    @Bean
    public KafkaTemplate<Long, LinkUpdate> kafkaTemplate(ProducerFactory<Long, LinkUpdate> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
