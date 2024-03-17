package edu.java.repository;

import edu.java.domain.StackOverflowQuestion;
import java.util.List;

public interface StackOverflowQuestionRepository {
    StackOverflowQuestion addStackOverflowQuestion(StackOverflowQuestion stackOverflowQuestion, Long linkId);

    List<StackOverflowQuestion> removeStackOverflowQuestions(Long linkId);

    List<StackOverflowQuestion> findStackOverflowQuestionsByLinkId(Long linkId);
}
