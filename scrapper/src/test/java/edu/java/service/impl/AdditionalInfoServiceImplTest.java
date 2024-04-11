package edu.java.service.impl;

import edu.java.client.github.GithubClient;
import edu.java.client.github.dto.RepositoryResponse;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.client.stackoverflow.dto.QuestionResponse;
import edu.java.domain.Link;
import edu.java.service.GithubRepoService;
import edu.java.service.StackOverflowQuestionService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdditionalInfoServiceImplTest {
    @InjectMocks
    private AdditionalInfoServiceImpl additionalInfoService;

    @Mock
    private GithubRepoService githubService;

    @Mock
    private GithubClient githubClient;

    @Mock
    private StackOverflowQuestionService stackOverflowService;

    @Mock
    private StackOverflowClient stackOverflowClient;

    @Test
    void removeAdditionalInfo_shouldRemoveGithubRepository_whenUrlContainsGithub() {
        Link link = Link.builder()
            .url(URI.create("github.com/TestOwnerUserName/TestGithubRepositoryName"))
            .build();

        additionalInfoService.removeAdditionalInfo(link);

        verify(githubService, times(1)).removeGithubRepository(link.getId());
    }

    @Test
    void removeAdditionalInfo_shouldRemoveStackOverflowQuestion_whenUrlContainsStackOverflow() {
        Link link = Link.builder()
            .url(URI.create("stackoverflow.com/questions/1"))
            .build();

        additionalInfoService.removeAdditionalInfo(link);

        verify(stackOverflowService, times(1)).removeStackOverflowQuestion(link.getId());
    }

    @Test
    void addAdditionalInfo_shouldAddGithubRepository_whenUrlContainsGithub() {
        Link link = Link.builder()
            .id(1L)
            .url(URI.create("github.com/TestOwnerUserName/TestGithubRepositoryName"))
            .build();

        String githubOwnerUserName = "TestOwnerUserName";
        String githubRepositoryName = "TestGithubRepositoryName";
        when(githubClient.getOwnerUserName(link.getUrl().toString())).thenReturn(githubOwnerUserName);
        when(githubClient.getRepositoryName(link.getUrl().toString())).thenReturn(githubRepositoryName);
        RepositoryResponse repositoryResponse = new RepositoryResponse(
            1L,
            githubRepositoryName,
            0,
            OffsetDateTime.now()
        );

        when(githubClient.fetchRepository(
            githubOwnerUserName,
            githubRepositoryName
        )).thenReturn(repositoryResponse);

        additionalInfoService.addAdditionalInfo(link);

        verify(githubClient, times(1)).getOwnerUserName(link.getUrl().toString());
        verify(githubClient, times(1)).getRepositoryName(link.getUrl().toString());
        verify(githubClient, times(1)).fetchRepository(
            githubOwnerUserName,
            githubRepositoryName
        );
        verify(githubService, times(1)).addGithubRepository(repositoryResponse, link.getId());
    }

    @Test
    void addAdditionalInfo_shouldAddStackOverflowQuestion_whenUrlContainsStackOverflow() {
        Link link = Link.builder()
            .id(1L)
            .url(URI.create("stackoverflow.com/questions/1"))
            .build();

        QuestionResponse questionResponse = new QuestionResponse(List.of(new QuestionResponse.ItemResponse(
            1L,
            "title",
            false,
            0,
            0L,
            OffsetDateTime.now()
        )));

        when(stackOverflowClient.getQuestionId(link.getUrl().toString())).thenReturn(1L);
        when(stackOverflowClient.fetchQuestion(link.getId())).thenReturn(questionResponse);

        additionalInfoService.addAdditionalInfo(link);

        verify(stackOverflowClient, times(1)).getQuestionId(link.getUrl().toString());
        verify(stackOverflowClient, times(1)).fetchQuestion(link.getId());
        verify(stackOverflowService, times(1)).addStackOverflowQuestion(questionResponse, link.getId());
    }
}
