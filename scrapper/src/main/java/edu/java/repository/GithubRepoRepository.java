package edu.java.repository;

import edu.java.domain.GithubRepository;

public interface GithubRepoRepository {
    GithubRepository addGithubRepository(GithubRepository githubRepository, Long linkId);

    GithubRepository removeGithubRepository(Long linkId);

    GithubRepository findGithubRepositoryByLinkId(long linkId);
}
