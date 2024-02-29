package edu.java.client;

import edu.java.response.QuestionResponse;

public interface StackOverflowClient {
    QuestionResponse fetchQuestion(Long questionId);
}
