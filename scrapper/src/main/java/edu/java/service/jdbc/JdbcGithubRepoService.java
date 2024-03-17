package edu.java.service.jdbc;

import edu.java.client.github.dto.RepositoryResponse;
import edu.java.domain.GithubRepository;
import edu.java.repository.GithubRepoRepository;
import edu.java.service.GithubRepoService;
import org.springframework.stereotype.Service;

@Service
public class JdbcGithubRepoService implements GithubRepoService {

    private final GithubRepoRepository githubRepository;

    public JdbcGithubRepoService(GithubRepoRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    @Override
    public GithubRepository addGithubRepository(RepositoryResponse repositoryResponse, Long linkId) {
        GithubRepository repository = GithubRepository.builder()
            .repositoryId(repositoryResponse.repositoryId())
            .fullName(repositoryResponse.fullName())
            .forksCount(repositoryResponse.forksCount())
            .updatedAt(repositoryResponse.updatedAt())
            .linkId(linkId)
            .build();
        return githubRepository.addGithubRepository(repository, linkId);
    }

    @Override
    public GithubRepository removeGithubRepository(Long linkId) {
        return githubRepository.removeGithubRepository(linkId);
    }

    @Override
    public GithubRepository getGithubRepositoryByLinkId(Long linkId) {
        return githubRepository.findGithubRepositoryByLinkId(linkId);
    }
}
