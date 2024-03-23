package edu.java.service.jdbc;

import edu.java.domain.Link;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import edu.java.repository.LinkRepository;
import edu.java.service.AdditionalInfoService;
import edu.java.service.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    private final AdditionalInfoService additionalInfoService;

    public JdbcLinkService(LinkRepository linkRepository, AdditionalInfoService additionalInfoService) {
        this.linkRepository = linkRepository;
        this.additionalInfoService = additionalInfoService;
    }

    @Override
    public LinkResponse addLink(long telegramChatId, URI url) {
        Link linkToAdd = Link.builder()
            .url(url)
            .build();
        if (linkRepository.isLinkExists(linkToAdd)) {
            Link link = linkRepository.findLinkByUrl(linkToAdd.getUrl());
            if (!linkRepository.isChatLinkRelationshipExists(telegramChatId, link.getId())) {
                linkRepository.addChatLinkRelationship(telegramChatId, link.getId());
            }
            return new LinkResponse(link.getId(), link.getUrl());
        } else {
            Link link = linkRepository.addLink(linkToAdd);
            additionalInfoService.addAdditionalInfo(link);
            linkRepository.addChatLinkRelationship(telegramChatId, link.getId());
            return new LinkResponse(link.getId(), link.getUrl());
        }
    }

    @Override
    public LinkResponse removeLink(long telegramChatId, URI url) {
        Link linkToRemove = Link.builder().url(url).build();
        if (linkRepository.isLinkExists(linkToRemove)) {
            Link linkWithId = linkRepository.findLinkByUrl(linkToRemove.getUrl());
            if (linkRepository.isChatLinkRelationshipExists(telegramChatId, linkWithId.getId())) {
                linkRepository.removeChatLinkRelationship(telegramChatId, linkWithId.getId());
            }
            if (!linkRepository.areThereAnyChatLinkRelationshipsByLinkId(linkWithId.getId())) {
                linkRepository.removeLink(linkWithId);
                additionalInfoService.removeAdditionalInfo(linkWithId);
            }
            return new LinkResponse(linkWithId.getId(), linkWithId.getUrl());
        }
        return null;
    }

    @Override
    public ListLinksResponse listAllLinks(long telegramChatId) {
        List<Link> linkList = linkRepository.findAllLinks(telegramChatId);
        List<LinkResponse> linkResponseList = linkList.stream()
            .map(link -> new LinkResponse(link.getId(), link.getUrl()))
            .toList();
        return new ListLinksResponse(linkResponseList, linkResponseList.size());
    }

    @Override
    public boolean isLinkConnectedToChat(long telegramChat, URI url) {
        Link linkToCheck = Link.builder().url(url).build();
        if (linkRepository.isLinkExists(linkToCheck)) {
            return linkRepository.isChatLinkRelationshipExists(telegramChat, telegramChat);
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
        return linkRepository.findLinkById(id);
    }

    @Override
    public void updateLastModifiedTime(long id, OffsetDateTime lastModifiedTime) {
        linkRepository.updateLinkLastModifiedTime(Link.builder().id(id).lastModifiedTime(lastModifiedTime).build());
    }

    @Override
    public void updateLastUpdateCheckTime(long id) {
        linkRepository.updateLinkLastUpdateCheckTime(Link.builder().id(id).build());
    }
}
