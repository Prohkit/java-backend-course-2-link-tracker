package edu.java.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepositoryResponse(
    @JsonProperty("id")
    Long repositoryId,
    @JsonProperty("full_name")
    String fullName
) {
}
