package edu.java.client.stackoverflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record QuestionResponse(@JsonProperty("items") List<ItemResponse> items) {
    public record ItemResponse(
        @JsonProperty("question_id")
        Long questionId,
        @JsonProperty("title")
        String title,
        @JsonProperty("is_answered")
        Boolean isAnswered,
        @JsonProperty("score")
        Integer score,

        @JsonProperty("answer_count")
        Long answerCount,

        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate
    ) {
    }
}
