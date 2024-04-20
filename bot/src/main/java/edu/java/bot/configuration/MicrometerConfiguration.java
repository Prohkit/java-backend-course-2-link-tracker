package edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfiguration {
    @Bean
    public Counter processedMessagesCounter(MeterRegistry registry, ApplicationConfig applicationConfig) {
        return Counter.builder(applicationConfig
                .micrometer()
                .processedMessagesCounter()
                .name())
            .description(applicationConfig
                .micrometer()
                .processedMessagesCounter()
                .description())
            .register(registry);
    }
}
