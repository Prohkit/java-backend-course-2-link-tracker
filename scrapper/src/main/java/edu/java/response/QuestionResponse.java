package edu.java.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record QuestionResponse(List<ItemResponse> items) {
    record ItemResponse(
        @JsonProperty("question_id")
        Long questionId,
        @JsonProperty("title")
        String title,
        @JsonProperty("is_answered")
        boolean isAnswered,
        @JsonProperty("score")
        int score) {
    }
}
