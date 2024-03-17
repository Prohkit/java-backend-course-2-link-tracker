package edu.java.dto.scrapper.request;

import java.net.URI;
import lombok.Data;

@Data
public class RemoveLinkRequest {
    private URI link;
}
