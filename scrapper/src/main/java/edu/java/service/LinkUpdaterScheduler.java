package edu.java.service;

import edu.java.client.BotClient;
import edu.java.client.ClientsChain;
import edu.java.domain.Link;
import edu.java.dto.bot.LinkUpdate;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LinkUpdaterScheduler {

    private final ClientsChain chain;

    private final BotClient botClient;

    private final LinkService linkService;

    private final TelegramChatService telegramChatService;

    public LinkUpdaterScheduler(
        ClientsChain chain,
        BotClient botClient,
        LinkService linkService,
        TelegramChatService telegramChatService
    ) {
        this.chain = chain;
        this.botClient = botClient;
        this.linkService = linkService;
        this.telegramChatService = telegramChatService;
    }

    @Value("${link-check-interval}")
    private Duration linkCheckInterval;

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        log.info("start update");
        List<Link> linksToUpdate = linkService.findLinksToCheck(linkCheckInterval);
        for (Link link : linksToUpdate) {
            String description = chain.getUpdateInfo(link);
            linkService.updateLastUpdateCheckTime(link.getId());

            Link linkFromDB = linkService.findLinkById(link.getId());

            OffsetDateTime previousLastModifiedTime = linkFromDB.getLastModifiedTime();
            if (previousLastModifiedTime.isBefore(link.getLastModifiedTime())) {
                linkService.updateLastModifiedTime(
                    link.getId(),
                    link.getLastModifiedTime()
                );
            }
            List<Long> telegramChatIds = telegramChatService.getTelegramChatIdsByLinkId(link.getId());
            LinkUpdate linkUpdate = LinkUpdate.builder()
                .id(link.getId())
                .url(link.getUrl())
                .description(description)
                .tgChatIds(telegramChatIds)
                .build();
            botClient.sendUpdate(linkUpdate);
        }
    }
}
