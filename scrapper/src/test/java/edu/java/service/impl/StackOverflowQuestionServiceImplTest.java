package edu.java.service.impl;

import edu.java.client.stackoverflow.dto.QuestionResponse;
import edu.java.domain.StackOverflowQuestion;
import edu.java.repository.StackOverflowQuestionRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StackOverflowQuestionServiceImplTest {
    @InjectMocks
    private StackOverflowQuestionServiceImpl stackOverflowQuestionService;

    @Mock
    private StackOverflowQuestionRepository stackOverflowRepository;

    @Test
    void addStackOverflowQuestion() {
        long linkId = 1L;

        QuestionResponse questionResponse = new QuestionResponse(
            List.of(
                new QuestionResponse.ItemResponse(
                    1L,
                    "title",
                    false,
                    0,
                    0L,
                    OffsetDateTime.MAX
                )
            )
        );

        StackOverflowQuestion question = StackOverflowQuestion.builder()
            .questionId(1L)
            .linkId(1L)
            .title("title")
            .isAnswered(false)
            .score(0)
            .answerCount(0L)
            .lastActivityDate(OffsetDateTime.MAX)
            .build();

        stackOverflowQuestionService.addStackOverflowQuestion(questionResponse, linkId);

        verify(stackOverflowRepository, times(1)).addStackOverflowQuestion(question, linkId);
    }

    @Test
    void removeStackOverflowQuestion() {
        long linkId = 1L;

        stackOverflowQuestionService.removeStackOverflowQuestion(linkId);

        verify(stackOverflowRepository, times(1)).removeStackOverflowQuestions(linkId);
    }

    @Test
    void getStackOverflowQuestionByLinkId() {
        long linkId = 1L;

        stackOverflowQuestionService.getStackOverflowQuestionByLinkId(linkId);

        verify(stackOverflowRepository, times(1)).findStackOverflowQuestionsByLinkId(linkId);
    }
}
