package edu.java.client;

import edu.java.response.RepositoryResponse;
import org.springframework.stereotype.Component;

@Component
public class GithubClientImpl extends HttpClient implements GithubClient {

    public GithubClientImpl() {
        clientHostName = "github.com";
    }

    @Override
    public RepositoryResponse fetchRepository(String repositoryName) {
        return null;
    }

    @Override
    public boolean getUpdateInfo(String url) {
        return handleNext(url);
    }
}
