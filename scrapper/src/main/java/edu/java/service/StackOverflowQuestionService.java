package edu.java.service;

import edu.java.client.stackoverflow.dto.QuestionResponse;
import edu.java.domain.StackOverflowQuestion;
import java.util.List;

public interface StackOverflowQuestionService {
    void addStackOverflowQuestion(QuestionResponse questionResponse, Long linkId);

    List<StackOverflowQuestion> removeStackOverflowQuestion(Long linkId);

    List<StackOverflowQuestion> getStackOverflowQuestionByLinkId(Long linkId);
}
