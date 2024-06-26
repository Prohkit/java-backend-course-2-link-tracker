package edu.java.service.notificationsender.http;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.dto.bot.LinkUpdate;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

@WireMockTest(httpPort = 8090)
@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class BotClientTest extends IntegrationTest {

    @Autowired
    private BotClient botClient;

    @Test
    void sendUpdate() {
        LinkUpdate linkUpdate = LinkUpdate.builder()
            .url(URI.create(""))
            .id(1L)
            .description("")
            .tgChatIds(List.of())
            .build();

        stubFor(post(urlEqualTo("/updates"))
            .willReturn(ok()));

        botClient.send(linkUpdate);

        verify(postRequestedFor(urlEqualTo("/updates")));
    }
}
