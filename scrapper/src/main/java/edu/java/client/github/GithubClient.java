package edu.java.client.github;

import edu.java.client.github.dto.RepositoryResponse;

public interface GithubClient {
    RepositoryResponse fetchRepository(String ownerUserName, String repositoryName);

    String getOwnerUserName(String url);

    String getRepositoryName(String url);
}
