package edu.java.client;

import edu.java.response.RepositoryResponse;

public interface GithubClient {
    RepositoryResponse fetchRepository(String ownerUserName, String repositoryName);
}
