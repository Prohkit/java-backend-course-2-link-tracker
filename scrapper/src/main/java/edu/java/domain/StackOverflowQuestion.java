package edu.java.domain;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StackOverflowQuestion {
    private Long questionId;
    private Long linkId;
    private String title;
    private boolean isAnswered;
    private Integer score;
    private Long answerCount;
    private OffsetDateTime lastActivityDate;
}
