package edu.java.client.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record RepositoryResponse(
    @JsonProperty("id")
    Long repositoryId,
    @JsonProperty("full_name")
    String fullName,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {
}
