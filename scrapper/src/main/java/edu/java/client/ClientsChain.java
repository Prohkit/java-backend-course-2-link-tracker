package edu.java.client;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class ClientsChain {

    private HttpClient client;

    private final GithubClientImpl githubClient;

    private final StackoverflowClientImpl stackoverflowClient;

    public ClientsChain(GithubClientImpl githubClient, StackoverflowClientImpl stackoverflowClient) {
        this.githubClient = githubClient;
        this.stackoverflowClient = stackoverflowClient;
    }

    @PostConstruct
    void init() {
        initChainCommandHanlders();
    }

    private void initChainCommandHanlders() {
        client = HttpClient.link(
            githubClient,
            stackoverflowClient
        );
    }

    public HttpClient getClient() {
        return client;
    }
}
