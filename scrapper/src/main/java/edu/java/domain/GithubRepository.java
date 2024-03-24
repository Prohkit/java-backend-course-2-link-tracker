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
@Table(name = "github_repository")
public class GithubRepository {
    @Id
    private Long repositoryId;
    private Long linkId;
    private String fullName;
    private Integer forksCount;
    private OffsetDateTime updatedAt;
}
