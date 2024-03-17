package edu.java.client.impl;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.client.stackoverflow.dto.QuestionResponse;
import edu.java.configuration.ClientConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 8082)
public class StackoverflowClientImplTest {

    private StackOverflowClient stackOverflowClient;

    @BeforeEach
    public void setUp() {
        stackOverflowClient = new ClientConfiguration().stackOverflowClient("http://localhost:8082");
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
