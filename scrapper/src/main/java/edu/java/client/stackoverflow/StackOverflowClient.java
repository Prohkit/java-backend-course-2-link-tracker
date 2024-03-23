package edu.java.client.stackoverflow;

import edu.java.client.stackoverflow.dto.QuestionResponse;

public interface StackOverflowClient {
    QuestionResponse fetchQuestion(Long questionId);

    Long getQuestionId(String url);
}
