package edu.java.controller;

import edu.java.dto.scrapper.request.AddLinkRequest;
import edu.java.dto.scrapper.request.RemoveLinkRequest;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import edu.java.service.LinkService;
import edu.java.service.TelegramChatService;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ScrapperControllerTest {

    @Mock
    private LinkService linkService;

    @Mock
    private TelegramChatService telegramChatService;

    @InjectMocks
    private ScrapperController scrapperController;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(scrapperController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void registerChat() throws Exception {
        mockMvc.perform(post("/tg-chat/{id}", 1L))
            .andExpect(status().isOk());
        verify(telegramChatService, times(1)).isChatExists(1L);
        verify(telegramChatService, times(1)).register(1L);
    }

    @Test
    void deleteChat() throws Exception {
        when(telegramChatService.isChatExists(1L)).thenReturn(true);
        mockMvc.perform(delete("/tg-chat/{id}", 1L))
            .andExpect(status().isOk());
        verify(telegramChatService, times(1)).isChatExists(1L);
        verify(telegramChatService, times(1)).unregister(1L);
    }

    @Test
    void getAllTrackedLinks() throws Exception {
        when(telegramChatService.isChatExists(1L)).thenReturn(true);
        List<LinkResponse> linkResponseList = List.of(new LinkResponse(1L, URI.create("stackoverflow.com")));
        ListLinksResponse listLinksResponse = new ListLinksResponse(linkResponseList, 1);
        when(linkService.listAllLinks(1L)).thenReturn(listLinksResponse);

        mockMvc.perform(get("/links").header("Tg-Chat-Id", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size").value(1));

        verify(telegramChatService, times(1)).isChatExists(1L);
        verify(linkService, times(1)).listAllLinks(1L);
    }

    @Test
    void addLinkTracking() throws Exception {
        when(telegramChatService.isChatExists(1L)).thenReturn(true);
        AddLinkRequest linkRequest = new AddLinkRequest(URI.create("stackoverflow.com"));

        when(linkService.isLinkConnectedToChat(1L, linkRequest.getLink())).thenReturn(false);
        LinkResponse linkResponse = new LinkResponse(1L, linkRequest.getLink());

        when(linkService.addLink(1L, linkRequest.getLink())).thenReturn(linkResponse);

        String linkRequestJson = objectMapper.writeValueAsString(linkRequest);

        mockMvc.perform(post("/links").header("Tg-Chat-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(linkRequestJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(linkResponse.getId()))
            .andExpect(jsonPath("$.url").value(linkResponse.getUrl().toString()));

        verify(telegramChatService, times(1)).isChatExists(1L);
        verify(linkService, times(1)).isLinkConnectedToChat(1L, linkRequest.getLink());
        verify(linkService, times(1)).addLink(1L, linkRequest.getLink());
    }

    @Test
    void removeLinkTracking() throws Exception {
        when(telegramChatService.isChatExists(1L)).thenReturn(true);
        RemoveLinkRequest linkRequest = new RemoveLinkRequest(URI.create("stackoverflow.com"));

        when(linkService.isLinkConnectedToChat(1L, linkRequest.getLink())).thenReturn(true);
        LinkResponse linkResponse = new LinkResponse(1L, linkRequest.getLink());

        when(linkService.removeLink(1L, linkRequest.getLink())).thenReturn(linkResponse);

        String linkRequestJson = objectMapper.writeValueAsString(linkRequest);

        mockMvc.perform(delete("/links").header("Tg-Chat-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(linkRequestJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(linkResponse.getId()))
            .andExpect(jsonPath("$.url").value(linkResponse.getUrl().toString()));

        verify(telegramChatService, times(1)).isChatExists(1L);
        verify(linkService, times(1)).isLinkConnectedToChat(1L, linkRequest.getLink());
        verify(linkService, times(1)).removeLink(1L, linkRequest.getLink());
    }
}
