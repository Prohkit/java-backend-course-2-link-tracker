package edu.java.bot.dto;

import java.util.List;
import lombok.Data;

@Data
public class LinkUpdate {
    private Long id;
    private String url;
    private String description;
    private List<Integer> tgChatIds;
}
