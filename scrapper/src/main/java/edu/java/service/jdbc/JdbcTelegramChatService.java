package edu.java.service.jdbc;

import edu.java.domain.Chat;
import edu.java.domain.Link;
import edu.java.repository.LinkRepository;
import edu.java.repository.TelegramChatRepository;
import edu.java.service.AdditionalInfoService;
import edu.java.service.TelegramChatService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JdbcTelegramChatService implements TelegramChatService {

    private final TelegramChatRepository telegramChatRepository;

    private final LinkRepository linkRepository;

    private final AdditionalInfoService additionalInfoService;

    public JdbcTelegramChatService(
        TelegramChatRepository telegramChatRepository,
        LinkRepository linkRepository,
        AdditionalInfoService additionalInfoService
    ) {
        this.telegramChatRepository = telegramChatRepository;
        this.linkRepository = linkRepository;
        this.additionalInfoService = additionalInfoService;
    }

    @Override
    public void register(long telegramChatId) {
        telegramChatRepository.addChat(Chat.builder().id(telegramChatId).build());
    }

    @Override
    public void unregister(long telegramChatId) {
        List<Long> linkIds = telegramChatRepository.removeChatLinkRelationships(telegramChatId);
        for (Long linkId : linkIds) {
            if (!linkRepository.areThereAnyChatLinkRelationshipsByLinkId(linkId)) {
                Link linkToRemove = linkRepository.findLinkById(linkId);
                linkRepository.removeLink(linkToRemove);
                additionalInfoService.removeAdditionalInfo(linkToRemove);
            }
        }
        telegramChatRepository.removeChat(Chat.builder().id(telegramChatId).build());
    }

    public boolean isChatExists(long telegramChatId) {
        return telegramChatRepository.isChatExists(Chat.builder().id(
            telegramChatId).build());
    }

    @Override
    public List<Long> getTelegramChatIdsByLinkId(long linkId) {
        return telegramChatRepository.getTelegramChatIdsByLinkId(linkId);
    }
}
