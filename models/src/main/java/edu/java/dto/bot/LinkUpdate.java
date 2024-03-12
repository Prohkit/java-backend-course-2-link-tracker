package edu.java.dto.bot;

import java.net.URI;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkUpdate {
    private Long id;
    private URI url;
    private String description;
    private List<Integer> tgChatIds;
}
