package edu.java.service.jpa;

import edu.java.client.stackoverflow.dto.QuestionResponse;
import edu.java.domain.StackOverflowQuestion;
import edu.java.repository.jpa.JpaStackOverflowQuestionRepository;
import edu.java.service.StackOverflowQuestionService;
import java.util.List;

public class JpaStackOverflowQuestionService implements StackOverflowQuestionService {

    private final JpaStackOverflowQuestionRepository stackOverflowRepository;

    public JpaStackOverflowQuestionService(JpaStackOverflowQuestionRepository stackOverflowRepository) {
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
            stackOverflowRepository.save(question);
        }
    }

    @Override
    public List<StackOverflowQuestion> removeStackOverflowQuestion(Long linkId) {
        List<StackOverflowQuestion> stackOverflowQuestionList = getStackOverflowQuestionByLinkId(linkId);
        stackOverflowRepository.deleteStackOverflowQuestionsByLinkId(linkId);
        return stackOverflowQuestionList;
    }

    @Override
    public List<StackOverflowQuestion> getStackOverflowQuestionByLinkId(Long linkId) {
        return stackOverflowRepository.findStackOverflowQuestionsByLinkId(linkId);
    }
}
