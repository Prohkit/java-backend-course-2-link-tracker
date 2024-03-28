package edu.java.service.impl;

import edu.java.client.stackoverflow.dto.QuestionResponse;
import edu.java.domain.StackOverflowQuestion;
import edu.java.repository.StackOverflowQuestionRepository;
import edu.java.service.StackOverflowQuestionService;
import java.util.List;

public class StackOverflowQuestionServiceImpl implements StackOverflowQuestionService {

    private final StackOverflowQuestionRepository stackOverflowRepository;

    public StackOverflowQuestionServiceImpl(StackOverflowQuestionRepository stackOverflowRepository) {
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
        return stackOverflowRepository.findStackOverflowQuestionsByLinkId(linkId);
    }
}
