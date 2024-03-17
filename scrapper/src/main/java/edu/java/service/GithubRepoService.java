package edu.java.service;

import edu.java.client.github.dto.RepositoryResponse;
import edu.java.domain.GithubRepository;

public interface GithubRepoService {
    GithubRepository addGithubRepository(RepositoryResponse repositoryResponse, Long linkId);

    GithubRepository removeGithubRepository(Long linkId);

    GithubRepository getGithubRepositoryByLinkId(Long linkId);
}
