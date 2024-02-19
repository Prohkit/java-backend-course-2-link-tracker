package edu.java.client;

import edu.java.client.impl.GithubClientImpl;
import edu.java.client.impl.StackoverflowClientImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class ClientsChain {

    private Client client;

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
        client = Client.link(
            githubClient,
            stackoverflowClient
        );
    }

    public Client getClient() {
        return client;
    }
}
