package edu.java.client.impl;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.client.stackoverflow.dto.QuestionResponse;
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

@WireMockTest(httpPort = 8082)
public class StackoverflowClientImplTest {

    private static StackOverflowClient stackOverflowClient;

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
        stackOverflowClient =
            new ClientConfiguration(githubRepoService, stackOverflowQuestionService, retryConfig).stackOverflowClient(
                "http://localhost:8082");
    }

    @Test
    public void testFetchPost() {
        long questionId = 1642028L;
        stubFor(get(urlEqualTo("/questions/" + questionId + "/?site=stackoverflow&filter=withbody"))
            .willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {
                      "items": [
                        {
                            "question_id": 1642028,
                            "title": "TITLE",
                            "is_answered": true,
                            "score": 20
                        }
                      ],
                      "has_more": false,
                      "quota_max": 10000,
                      "quota_remaining": 9926
                    }
                    """)
            ));
        QuestionResponse response = stackOverflowClient.fetchQuestion(questionId);

        assertThat(response)
            .isNotNull()
            .extracting(
                questionResponse -> questionResponse.items().get(0).questionId(),
                questionResponse -> questionResponse.items().get(0).title(),
                questionResponse -> questionResponse.items().get(0).isAnswered(),
                questionResponse -> questionResponse.items().get(0).score()
            ).containsExactly(
                questionId,
                "TITLE",
                true,
                20
            );
    }
}
