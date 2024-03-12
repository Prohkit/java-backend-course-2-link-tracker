package edu.java.dto.scrapper.response;

import java.util.List;
import lombok.Data;

@Data
public class ListLinksResponse {
    private List<LinkResponse> links;
    Integer size;
}
