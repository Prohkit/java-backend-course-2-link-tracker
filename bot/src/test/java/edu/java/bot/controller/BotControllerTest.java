package edu.java.bot.controller;

import edu.java.bot.service.LinkUpdateService;
import edu.java.dto.bot.LinkUpdate;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BotControllerTest {
    @InjectMocks
    private BotController botController;

    @Mock
    private LinkUpdateService linkUpdateService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(botController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void sendResponse() throws Exception {
        LinkUpdate linkUpdate = LinkUpdate.builder()
            .id(1L)
            .url(URI.create("link"))
            .description("description")
            .tgChatIds(List.of(1L))
            .build();

        String linkUpdateJson = objectMapper.writeValueAsString(linkUpdate);

        mockMvc.perform(post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(linkUpdateJson))
            .andExpect(status().isOk());

        verify(linkUpdateService, times(1)).sendNotification(linkUpdate);
    }
}
