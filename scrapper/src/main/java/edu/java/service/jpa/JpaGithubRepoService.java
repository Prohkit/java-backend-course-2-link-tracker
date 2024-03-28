package edu.java.service.jpa;

import edu.java.client.github.dto.RepositoryResponse;
import edu.java.domain.GithubRepository;
import edu.java.repository.jpa.JpaGithubRepoRepository;
import edu.java.service.GithubRepoService;

public class JpaGithubRepoService implements GithubRepoService {

    private final JpaGithubRepoRepository githubRepository;

    public JpaGithubRepoService(JpaGithubRepoRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    @Override
    public GithubRepository addGithubRepository(RepositoryResponse repositoryResponse, Long linkId) {
        GithubRepository repository = GithubRepository.builder()
            .repositoryId(repositoryResponse.repositoryId())
            .linkId(linkId)
            .fullName(repositoryResponse.fullName())
            .forksCount(repositoryResponse.forksCount())
            .updatedAt(repositoryResponse.updatedAt())
            .build();
        return githubRepository.save(repository);
    }

    @Override
    public GithubRepository removeGithubRepository(Long linkId) {
        GithubRepository githubRepositoryToReturn = getGithubRepositoryByLinkId(linkId);
        githubRepository.deleteGithubRepositoryByLinkId(linkId);
        return githubRepositoryToReturn;
    }

    @Override
    public GithubRepository getGithubRepositoryByLinkId(Long linkId) {
        return githubRepository.findGithubRepositoryByLinkId(linkId);
    }
}
