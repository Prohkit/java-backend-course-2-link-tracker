package edu.java.dto;

import java.util.List;
import lombok.Data;

@Data
public class ListLinksResponse {
    private List<LinkResponse> links;
    Integer size;
}
