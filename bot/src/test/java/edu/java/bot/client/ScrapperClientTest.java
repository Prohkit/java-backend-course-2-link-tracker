package edu.java.bot.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.bot.exception.handler.ApiErrorResponse;
import edu.java.bot.service.SendMessageService;
import edu.java.dto.scrapper.request.AddLinkRequest;
import edu.java.dto.scrapper.request.RemoveLinkRequest;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
@SpringBootTest
class ScrapperClientTest {

    @Autowired
    private ScrapperClient scrapperClient;

    @MockBean
    private SendMessageService service;

    @Test
    void registerChat() {
        Long id = 1L;
        stubFor(post(urlEqualTo("/tg-chat/" + id))
            .willReturn(ok()));

        ResponseEntity<Void> responseEntity = scrapperClient.registerChat(id);

        verify(postRequestedFor(urlEqualTo("/tg-chat/" + id)));
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
    }

    @Test
    void registerChatReturnsBadRequest() throws JsonProcessingException {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
            .exceptionMessage("")
            .code("")
            .description("")
            .stacktrace(new ArrayList<>())
            .build();

        ObjectMapper objectMapper = new ObjectMapper();

        Long id = 1L;
        stubFor(post(urlEqualTo("/tg-chat/" + id))
            .willReturn(badRequest()
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(apiErrorResponse))
            ));

        scrapperClient.registerChat(id);

        Mockito.verify(service).sendApiError(id, apiErrorResponse);
        verify(postRequestedFor(urlEqualTo("/tg-chat/" + id)));
    }

    @Test
    void deleteChat() {
        Long id = 1L;
        stubFor(delete(urlEqualTo("/tg-chat/" + id))
            .willReturn(ok()));

        scrapperClient.deleteChat(id);

        verify(deleteRequestedFor(urlEqualTo("/tg-chat/" + id)));
    }

    @Test
    void deleteChatReturnsBadRequest() throws JsonProcessingException {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
            .exceptionMessage("")
            .code("")
            .description("")
            .stacktrace(new ArrayList<>())
            .build();

        ObjectMapper objectMapper = new ObjectMapper();

        Long id = 1L;
        stubFor(delete(urlEqualTo("/tg-chat/" + id))
            .willReturn(badRequest()
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(apiErrorResponse))
            ));

        scrapperClient.deleteChat(id);

        Mockito.verify(service).sendApiError(id, apiErrorResponse);
        verify(deleteRequestedFor(urlEqualTo("/tg-chat/" + id)));
    }

    @Test
    void getAllTrackedLinks() throws JsonProcessingException {
        ListLinksResponse listLinksResponse = new ListLinksResponse();
        listLinksResponse.setLinks(List.of(new LinkResponse()));
        listLinksResponse.setSize(1);

        ObjectMapper objectMapper = new ObjectMapper();

        Long chatId = 1L;
        stubFor(get(urlEqualTo("/links"))
            .willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withHeader("Tg-Chat-Id", "1")
                .withBody(objectMapper.writeValueAsString(listLinksResponse))
            ));

        ResponseEntity<ListLinksResponse> responseEntity = scrapperClient.getAllTrackedLinks(chatId);

        verify(getRequestedFor(urlEqualTo("/links")));
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
    }

    @Test
    void getAllTrackedLinksReturnsBadRequest() throws JsonProcessingException {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
            .exceptionMessage("")
            .code("")
            .description("")
            .stacktrace(new ArrayList<>())
            .build();

        ObjectMapper objectMapper = new ObjectMapper();

        Long chatId = 1L;
        stubFor(get(urlEqualTo("/links"))
            .willReturn(badRequest()
                .withHeader("Content-Type", "application/json")
                .withHeader("Tg-Chat-Id", "1")
                .withBody(objectMapper.writeValueAsString(apiErrorResponse))
            ));

        scrapperClient.getAllTrackedLinks(chatId);

        Mockito.verify(service).sendApiError(chatId, apiErrorResponse);
        verify(getRequestedFor(urlEqualTo("/links")));
    }

    @Test
    void addLinkTracking() throws JsonProcessingException {
        AddLinkRequest addLinkRequest = new AddLinkRequest();
        addLinkRequest.setLink(URI.create(""));

        Long chatId = 1L;

        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setId(chatId);
        linkResponse.setUrl(URI.create(""));

        ObjectMapper objectMapper = new ObjectMapper();

        stubFor(post(urlEqualTo("/links"))
            .willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withHeader("Tg-Chat-Id", "1")
                .withBody(objectMapper.writeValueAsString(linkResponse))
            ));

        ResponseEntity<LinkResponse> responseEntity = scrapperClient.addLinkTracking(chatId, addLinkRequest);

        verify(postRequestedFor(urlEqualTo("/links")));
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
    }

    @Test
    void addLinkTrackingReturnsBadRequest() throws JsonProcessingException {
        AddLinkRequest addLinkRequest = new AddLinkRequest();
        addLinkRequest.setLink(URI.create(""));

        Long chatId = 1L;

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
            .exceptionMessage("")
            .code("")
            .description("")
            .stacktrace(new ArrayList<>())
            .build();

        ObjectMapper objectMapper = new ObjectMapper();

        stubFor(post(urlEqualTo("/links"))
            .willReturn(badRequest()
                .withHeader("Content-Type", "application/json")
                .withHeader("Tg-Chat-Id", "1")
                .withBody(objectMapper.writeValueAsString(apiErrorResponse))
            ));

        scrapperClient.addLinkTracking(chatId, addLinkRequest);

        Mockito.verify(service).sendApiError(chatId, apiErrorResponse);
        verify(postRequestedFor(urlEqualTo("/links")));
    }

    @Test
    void removeLinkTracking() throws JsonProcessingException {
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest();
        removeLinkRequest.setLink(URI.create(""));

        Long chatId = 1L;

        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setId(chatId);
        linkResponse.setUrl(URI.create(""));

        ObjectMapper objectMapper = new ObjectMapper();

        stubFor(delete(urlEqualTo("/links"))
            .willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withHeader("Tg-Chat-Id", "1")
                .withBody(objectMapper.writeValueAsString(linkResponse))
            ));

        ResponseEntity<Void> responseEntity = scrapperClient.removeLinkTracking(chatId, removeLinkRequest);

        verify(deleteRequestedFor(urlEqualTo("/links")));
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
    }

    @Test
    void removeLinkTrackingReturnsBadRequest() throws JsonProcessingException {
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest();
        removeLinkRequest.setLink(URI.create(""));

        Long chatId = 1L;

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
            .exceptionMessage("")
            .code("")
            .description("")
            .stacktrace(new ArrayList<>())
            .build();

        ObjectMapper objectMapper = new ObjectMapper();

        stubFor(delete(urlEqualTo("/links"))
            .willReturn(badRequest()
                .withHeader("Content-Type", "application/json")
                .withHeader("Tg-Chat-Id", "1")
                .withBody(objectMapper.writeValueAsString(apiErrorResponse))
            ));

        scrapperClient.removeLinkTracking(chatId, removeLinkRequest);

        Mockito.verify(service).sendApiError(chatId, apiErrorResponse);
        verify(deleteRequestedFor(urlEqualTo("/links")));
    }
}
