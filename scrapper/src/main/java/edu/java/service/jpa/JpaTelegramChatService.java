package edu.java.service.jpa;

import edu.java.domain.Chat;
import edu.java.domain.Link;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.repository.jpa.JpaTelegramChatRepository;
import edu.java.service.AdditionalInfoService;
import edu.java.service.TelegramChatService;
import java.util.List;

public class JpaTelegramChatService implements TelegramChatService {

    private final JpaTelegramChatRepository telegramChatRepository;

    private final JpaLinkRepository linkRepository;

    private final AdditionalInfoService additionalInfoService;

    public JpaTelegramChatService(
        JpaTelegramChatRepository telegramChatRepository,
        JpaLinkRepository linkRepository,
        AdditionalInfoService additionalInfoService
    ) {
        this.telegramChatRepository = telegramChatRepository;
        this.linkRepository = linkRepository;
        this.additionalInfoService = additionalInfoService;
    }

    @Override
    public void register(long telegramChatId) {
        telegramChatRepository.save(Chat.builder().id(telegramChatId).build());
    }

    @Override
    public void unregister(long telegramChatId) {
        List<Long> linkIds = telegramChatRepository.removeChatLinkRelationships(telegramChatId);
        for (Long linkId : linkIds) {
            if (!linkRepository.areThereAnyChatLinkRelationshipsByLinkId(linkId)) {
                Link linkToRemove = linkRepository.findById(linkId).orElseThrow();
                linkRepository.deleteById(linkId);
                additionalInfoService.removeAdditionalInfo(linkToRemove);
            }
        }
        telegramChatRepository.delete(Chat.builder().id(telegramChatId).build());
    }

    public boolean isChatExists(long telegramChatId) {
        return telegramChatRepository.existsById(telegramChatId);
    }

    @Override
    public List<Long> getTelegramChatIdsByLinkId(long linkId) {
        return telegramChatRepository.getTelegramChatIdsByLinkId(linkId);
    }
}
