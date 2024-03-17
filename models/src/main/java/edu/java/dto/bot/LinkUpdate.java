package edu.java.dto.bot;

import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkUpdate {
    private Long id;
    private URI url;
    private String description;
    private List<Long> tgChatIds;
}
