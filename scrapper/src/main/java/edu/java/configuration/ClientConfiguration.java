package edu.java.configuration;

import edu.java.client.GithubClient;
import edu.java.client.GithubClientImpl;
import edu.java.client.StackoverflowClient;
import edu.java.client.StackoverflowClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Bean GithubClient githubClient() {
        return new GithubClientImpl();
    }

    @Bean StackoverflowClient stackoverflowClient() {
        return new StackoverflowClientImpl();
    }
}
