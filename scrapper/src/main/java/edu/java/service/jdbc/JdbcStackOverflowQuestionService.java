package edu.java.service.jdbc;

import edu.java.client.stackoverflow.dto.QuestionResponse;
import edu.java.domain.StackOverflowQuestion;
import edu.java.repository.StackOverflowQuestionRepository;
import edu.java.service.StackOverflowQuestionService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JdbcStackOverflowQuestionService implements StackOverflowQuestionService {

    private final StackOverflowQuestionRepository stackOverflowRepository;

    public JdbcStackOverflowQuestionService(StackOverflowQuestionRepository stackOverflowRepository) {
        this.stackOverflowRepository = stackOverflowRepository;
    }

    @Override
    public void addStackOverflowQuestion(QuestionResponse questionResponse, Long linkId) {
        for (QuestionResponse.ItemResponse item : questionResponse.items()) {
            StackOverflowQuestion question = StackOverflowQuestion.builder()
                .questionId(item.questionId())
                .linkId(linkId)
                .title(item.title())
                .isAnswered(item.isAnswered())
                .score(item.score())
                .answerCount(item.answerCount())
                .lastActivityDate(item.lastActivityDate())
                .build();
            stackOverflowRepository.addStackOverflowQuestion(question, linkId);
        }
    }

    @Override
    public List<StackOverflowQuestion> removeStackOverflowQuestion(Long linkId) {
        return stackOverflowRepository.removeStackOverflowQuestions(linkId);
    }

    @Override
    public List<StackOverflowQuestion> getStackOverflowQuestionByLinkId(Long linkId) {
        return null;
    }
}
