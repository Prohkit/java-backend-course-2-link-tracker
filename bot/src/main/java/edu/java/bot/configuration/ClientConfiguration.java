package edu.java.bot.configuration;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.service.SendMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    private final SendMessageService service;

    public ClientConfiguration(SendMessageService service) {
        this.service = service;
    }

    @Bean
    public ScrapperClient scrapperClient(@Value("http://localhost:8080") String baseUrl) {
        return new ScrapperClient(WebClient.builder().baseUrl(baseUrl).build(), service);
    }
}
