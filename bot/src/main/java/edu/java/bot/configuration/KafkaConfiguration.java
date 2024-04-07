package edu.java.bot.configuration;

import edu.java.dto.bot.LinkUpdate;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
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
    public ProducerFactory<Long, LinkUpdate> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProps());
    }

    private Map<String, Object> producerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            applicationConfig.kafkaConfigurationProperties().bootstrapServers()
        );
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<Long, LinkUpdate> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps());
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            applicationConfig.kafkaConfigurationProperties().bootstrapServers()
        );
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, LinkUpdate.class);
        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, LinkUpdate>
    kafkaListenerContainerFactory(ConsumerFactory<Long, LinkUpdate> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, LinkUpdate> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public KafkaTemplate<Long, LinkUpdate> kafkaTemplate(ProducerFactory<Long, LinkUpdate> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
