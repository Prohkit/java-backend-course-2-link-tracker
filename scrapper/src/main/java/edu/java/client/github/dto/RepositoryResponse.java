package edu.java.client.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepositoryResponse(
    @JsonProperty("id")
    Long repositoryId,
    @JsonProperty("full_name")
    String fullName
) {
}
