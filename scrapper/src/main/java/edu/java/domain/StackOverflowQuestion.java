package edu.java.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stackoverflow_question")
public class StackOverflowQuestion {
    @Id
    private Long questionId;
    private Long linkId;
    private String title;
    private boolean isAnswered;
    private Integer score;
    private Long answerCount;
    private OffsetDateTime lastActivityDate;
}
