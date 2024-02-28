package edu.java.configuration;

import edu.java.client.github.impl.GithubClientImpl;
import edu.java.client.stackoverflow.impl.StackOverflowClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public GithubClientImpl githubClient(@Value("https://api.github.com/") String baseUrl) {
        return new GithubClientImpl(WebClient.builder().baseUrl(baseUrl).build());
    }

    @Bean
    public StackOverflowClientImpl stackOverflowClient(@Value("https://api.stackexchange.com/2.3/") String baseUrl) {
        return new StackOverflowClientImpl(WebClient.builder().baseUrl(baseUrl).build());
    }
}
