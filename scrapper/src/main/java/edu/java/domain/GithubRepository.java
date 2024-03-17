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
public class GithubRepository {
    private Long repositoryId;
    private Long linkId;
    private String fullName;
    private Integer forksCount;
    private OffsetDateTime updatedAt;
}
