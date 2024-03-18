package edu.java.client.impl;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.github.dto.RepositoryResponse;
import edu.java.client.github.impl.GithubClientImpl;
import edu.java.configuration.ClientConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 8082)
public class GithubClientImplTest {

    private GithubClientImpl githubClient;

    @BeforeEach
    public void setUp() {
        githubClient = new ClientConfiguration().githubClient("http://localhost:8082");
    }

    @Test
    public void testFetchPost() {
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
}
