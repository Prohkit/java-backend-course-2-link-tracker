package edu.java.client.impl;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.github.GithubClient;
import edu.java.client.github.dto.RepositoryResponse;
import edu.java.configuration.ClientConfiguration;
import edu.java.configuration.RetryConfig;
import edu.java.service.GithubRepoService;
import edu.java.service.StackOverflowQuestionService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@WireMockTest(httpPort = 8082)
public class GithubClientImplTest {

    private static GithubClient githubClient;

    private static GithubRepoService githubRepoService;

    private static StackOverflowQuestionService stackOverflowQuestionService;

    @BeforeAll
    public static void setUp() {
        RetryConfig retryConfig = new RetryConfig();
        ReflectionTestUtils.setField(retryConfig, "maxAttempts", 5);
        ReflectionTestUtils.setField(retryConfig, "initialDelay", 1);
        ReflectionTestUtils.setField(retryConfig, "maxDelay", 10);
        ReflectionTestUtils.setField(retryConfig, "statusCodes", List.of(500, 502, 503, 504));
        ReflectionTestUtils.setField(retryConfig, "linearMultiplier", 2);
        githubClient =
            new ClientConfiguration(githubRepoService, stackOverflowQuestionService, retryConfig).githubClient(
                "http://localhost:8082");
    }

    @Test
    public void fetchRepository_shouldReturnRepositoryResponse() {
        String ownerUserName = "Prohkit";
        String repositoryName = "java-backend-course-2-link-tracker";
        stubFor(get(urlEqualTo("/repos/" + ownerUserName + "/" + repositoryName))
            .willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                        {
                            "id": 1642028,
                            "full_name": "Prohkit/java-backend-course-2-link-tracker"
                        }
                    """)
            ));
        RepositoryResponse response = githubClient.fetchRepository(ownerUserName, repositoryName);

        assertThat(response)
            .isNotNull()
            .extracting(
                RepositoryResponse::repositoryId,
                RepositoryResponse::fullName
            ).containsExactly(
                1642028L,
                "Prohkit/java-backend-course-2-link-tracker"
            );
    }

    @Test
    void getOwnerUserName_shouldReturnOwnerUserName_whenUrlIsCorrect() {
        String urlWithOwnerUserName = "github.com/TestOwnerUserName/TestGithubRepositoryName";
        String excepted = "TestOwnerUserName";

        String result = githubClient.getOwnerUserName(urlWithOwnerUserName);

        assertEquals(result, excepted);
    }

    @Test
    void getOwnerUserName_shouldReturnNull_whenUrlIsIncorrect() {
        String incorrectGithubUrl = "githab.com/TestOwnerUserName/TestGithubRepositoryName";

        String result = githubClient.getOwnerUserName(incorrectGithubUrl);

        assertNull(result);
    }

    @Test
    void getRepositoryName_shouldReturnOwnerUserName_whenUrlIsCorrect() {
        String urlWithRepositoryName = "github.com/TestOwnerUserName/TestGithubRepositoryName";
        String excepted = "TestGithubRepositoryName";

        String result = githubClient.getRepositoryName(urlWithRepositoryName);

        assertEquals(result, excepted);
    }

    @Test
    void getRepositoryName_shouldReturnNull_whenUrlIsIncorrect() {
        String incorrectGithubUrl = "githab.com/TestOwnerUserName/TestGithubRepositoryName";

        String result = githubClient.getRepositoryName(incorrectGithubUrl);

        assertNull(result);
    }
}
