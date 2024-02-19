package edu.java.client;

import edu.java.response.QuestionResponse;

public interface StackoverflowClient {
    QuestionResponse fetchQuestion(Long questionId);
}
