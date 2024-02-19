package edu.java.client.impl;

import edu.java.client.Client;
import edu.java.client.GithubClient;
import edu.java.response.RepositoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GithubClientImpl extends Client implements GithubClient {

    @Value("${url.site.github}")
    private String clientHostName;

    @Value("${url.api.github}")
    private String apiUrl;

    @Override
    public RepositoryResponse fetchRepository(String repositoryName) {
        return null;
    }

    @Override
    public boolean getUpdateInfo(String url) {
        return handleNext(url);
    }
}
