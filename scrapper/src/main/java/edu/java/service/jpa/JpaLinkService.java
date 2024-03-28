package edu.java.service.jpa;

import edu.java.domain.Link;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.AdditionalInfoService;
import edu.java.service.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class JpaLinkService implements LinkService {

    private final JpaLinkRepository linkRepository;

    private final AdditionalInfoService additionalInfoService;

    public JpaLinkService(JpaLinkRepository linkRepository, AdditionalInfoService additionalInfoService) {
        this.linkRepository = linkRepository;
        this.additionalInfoService = additionalInfoService;
    }

    @Override
    public LinkResponse addLink(long telegramChatId, URI url) {
        Link linkToAdd = Link.builder()
            .url(url)
            .lastModifiedTime(OffsetDateTime.now())
            .build();
        if (linkRepository.existsLinkByUrl(linkToAdd.getUrl().toString())) {
            Link link = linkRepository.findLinkByUrl(linkToAdd.getUrl());
            if (!linkRepository.isChatLinkRelationshipExists(telegramChatId, link.getId())) {
                linkRepository.addChatLinkRelationship(telegramChatId, link.getId());
            }
            return new LinkResponse(link.getId(), link.getUrl());
        } else {
            Link link = linkRepository.save(linkToAdd);
            additionalInfoService.addAdditionalInfo(link);
            linkRepository.addChatLinkRelationship(telegramChatId, link.getId());
            return new LinkResponse(link.getId(), link.getUrl());
        }
    }

    @Override
    public LinkResponse removeLink(long telegramChatId, URI url) {
        Link linkToRemove = Link.builder().url(url).build();
        if (linkRepository.existsLinkByUrl(linkToRemove.getUrl().toString())) {
            Link linkWithId = linkRepository.findLinkByUrl(linkToRemove.getUrl());
            if (linkRepository.isChatLinkRelationshipExists(telegramChatId, linkWithId.getId())) {
                linkRepository.removeChatLinkRelationship(telegramChatId, linkWithId.getId());
            }
            if (!linkRepository.areThereAnyChatLinkRelationshipsByLinkId(linkWithId.getId())) {
                linkRepository.deleteById(linkWithId.getId());
                additionalInfoService.removeAdditionalInfo(linkWithId);
            }
            return new LinkResponse(linkWithId.getId(), linkWithId.getUrl());
        }
        return null;
    }

    @Override
    public ListLinksResponse listAllLinks(long telegramChatId) {
        List<Link> linkList = linkRepository.findLinksByChatId(telegramChatId);
        List<LinkResponse> linkResponseList = linkList.stream()
            .map(link -> new LinkResponse(link.getId(), link.getUrl()))
            .toList();
        return new ListLinksResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public boolean isLinkConnectedToChat(long telegramChat, URI url) {
        Link linkToCheck = Link.builder().url(url).build();
        if (linkRepository.existsLinkByUrl(linkToCheck.getUrl().toString())) {
            Link link = linkRepository.findLinkByUrl(url);
            return linkRepository.isChatLinkRelationshipExists(telegramChat, link.getId());
        }
        return false;
    }

    @Override
    public List<Link> findLinksToCheck(Duration interval) {
        Timestamp timeToCheck = Timestamp.from(OffsetDateTime.now().minusSeconds(interval.getSeconds()).toInstant());
        return linkRepository.findLinksToCheck(timeToCheck);
    }

    @Override
    public Link findLinkByUrl(URI url) {
        return linkRepository.findLinkByUrl(url);
    }

    @Override
    public Link findLinkById(Long id) {
        return linkRepository.findById(id).orElseThrow();
    }

    @Override
    public void updateLastModifiedTime(long id, OffsetDateTime lastModifiedTime) {
        linkRepository.updateLinkLastModifiedTime(id, lastModifiedTime);
    }

    @Override
    public void updateLastUpdateCheckTime(long id) {
        linkRepository.updateLinkLastUpdateCheckTime(id);
    }
}
