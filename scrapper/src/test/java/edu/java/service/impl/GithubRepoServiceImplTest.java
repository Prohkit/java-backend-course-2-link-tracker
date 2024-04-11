package edu.java.service.impl;

import edu.java.client.github.dto.RepositoryResponse;
import edu.java.domain.GithubRepository;
import edu.java.repository.GithubRepoRepository;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GithubRepoServiceImplTest {

    @InjectMocks
    private GithubRepoServiceImpl githubRepoService;

    @Mock
    private GithubRepoRepository githubRepository;

    @Test
    void addGithubRepository() {
        RepositoryResponse repositoryResponse = new RepositoryResponse(
            1L,
            "TestGithubRepositoryName",
            0,
            OffsetDateTime.now()
        );

        Long linkId = 1L;

        GithubRepository repository = GithubRepository.builder()
            .repositoryId(repositoryResponse.repositoryId())
            .fullName(repositoryResponse.fullName())
            .forksCount(repositoryResponse.forksCount())
            .updatedAt(repositoryResponse.updatedAt())
            .linkId(linkId)
            .build();

        githubRepoService.addGithubRepository(repositoryResponse, linkId);

        verify(githubRepository, times(1)).addGithubRepository(repository, linkId);
    }

    @Test
    void removeGithubRepository() {
        Long linkId = 1L;

        githubRepoService.removeGithubRepository(linkId);

        verify(githubRepository, times(1)).removeGithubRepository(linkId);
    }

    @Test
    void getGithubRepositoryByLinkId_shouldFindGithubRepositoryByLinkId() {
        long linkId = 1L;

        githubRepoService.getGithubRepositoryByLinkId(linkId);

        verify(githubRepository, times(1)).findGithubRepositoryByLinkId(linkId);
    }
}
