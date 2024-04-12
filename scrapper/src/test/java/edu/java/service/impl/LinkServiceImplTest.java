package edu.java.service.impl;

import edu.java.domain.Link;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import edu.java.repository.LinkRepository;
import edu.java.service.AdditionalInfoService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {
    @InjectMocks
    private LinkServiceImpl linkService;

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private AdditionalInfoService additionalInfoService;

    @Test
    void addLink_shouldNotAddLink_whenLinkExists() {
        long telegramChatId = 1L;
        URI url = URI.create("link");
        Link linkToAdd = Link.builder()
            .url(url)
            .build();

        Link link = Link.builder()
            .id(1L)
            .url(url)
            .build();

        LinkResponse excepted = new LinkResponse(link.getId(), link.getUrl());

        when(linkRepository.isLinkExists(linkToAdd)).thenReturn(true);
        when(linkRepository.findLinkByUrl(linkToAdd.getUrl())).thenReturn(link);
        when(linkRepository.isChatLinkRelationshipExists(telegramChatId, link.getId())).thenReturn(true);

        LinkResponse result = linkService.addLink(telegramChatId, url);

        verify(linkRepository, times(1)).isLinkExists(linkToAdd);
        verify(linkRepository, times(1)).findLinkByUrl(linkToAdd.getUrl());
        verify(linkRepository, times(1)).isChatLinkRelationshipExists(telegramChatId, link.getId());
        verify(linkRepository, never()).addChatLinkRelationship(telegramChatId, link.getId());
        verify(linkRepository, never()).addLink(linkToAdd);

        assertEquals(result, excepted);
    }

    @Test
    void addLink_shouldAddChatLinkRelationship_whenChatLinkRelationshipDoesNotExist() {
        long telegramChatId = 1L;
        URI url = URI.create("link");
        Link linkToAdd = Link.builder()
            .url(url)
            .build();

        Link link = Link.builder()
            .id(1L)
            .url(url)
            .build();

        LinkResponse excepted = new LinkResponse(link.getId(), link.getUrl());

        when(linkRepository.isLinkExists(linkToAdd)).thenReturn(true);
        when(linkRepository.findLinkByUrl(linkToAdd.getUrl())).thenReturn(link);
        when(linkRepository.isChatLinkRelationshipExists(telegramChatId, link.getId())).thenReturn(false);

        LinkResponse result = linkService.addLink(telegramChatId, url);

        verify(linkRepository, times(1)).isLinkExists(linkToAdd);
        verify(linkRepository, times(1)).findLinkByUrl(linkToAdd.getUrl());
        verify(linkRepository, times(1)).isChatLinkRelationshipExists(telegramChatId, link.getId());
        verify(linkRepository, times(1)).addChatLinkRelationship(telegramChatId, link.getId());
        verify(linkRepository, never()).addLink(linkToAdd);

        assertEquals(result, excepted);
    }

    @Test
    void addLink_shouldAddLink_whenLinkDoesNotExist() {
        long telegramChatId = 1L;
        URI url = URI.create("link");
        Link linkToAdd = Link.builder()
            .url(url)
            .build();

        Link link = Link.builder()
            .id(1L)
            .url(url)
            .build();

        LinkResponse excepted = new LinkResponse(link.getId(), link.getUrl());

        when(linkRepository.isLinkExists(linkToAdd)).thenReturn(false);
        when(linkRepository.addLink(linkToAdd)).thenReturn(link);

        LinkResponse result = linkService.addLink(telegramChatId, url);

        verify(linkRepository, times(1)).isLinkExists(linkToAdd);
        verify(linkRepository, times(1)).addLink(linkToAdd);
        verify(additionalInfoService, times(1)).addAdditionalInfo(link);
        verify(linkRepository, times(1)).addChatLinkRelationship(telegramChatId, link.getId());

        assertEquals(result, excepted);
    }

    @Test
    void removeLink_shouldReturnNull_whenLinkDoesNotExist() {
        long telegramChatId = 1L;
        URI url = URI.create("link");
        Link linkToRemove = Link.builder()
            .url(url)
            .build();

        when(linkRepository.isLinkExists(linkToRemove)).thenReturn(false);

        LinkResponse result = linkService.removeLink(telegramChatId, url);

        assertNull(result);
    }

    @Test
    void removeLink_shouldRemoveChatLinkRelationship_whenChatLinkRelationshipExists() {
        long telegramChatId = 1L;
        URI url = URI.create("link");
        Link linkToRemove = Link.builder()
            .url(url)
            .build();

        Link link = Link.builder()
            .id(1L)
            .url(url)
            .build();

        when(linkRepository.isLinkExists(linkToRemove)).thenReturn(true);
        when(linkRepository.findLinkByUrl(linkToRemove.getUrl())).thenReturn(link);
        when(linkRepository.isChatLinkRelationshipExists(telegramChatId, link.getId())).thenReturn(true);

        linkService.removeLink(telegramChatId, url);

        verify(linkRepository, times(1)).removeChatLinkRelationship(telegramChatId, link.getId());
    }

    @Test
    void removeLink_whenAnyChatLinkRelationshipsDoesNotExist() {
        long telegramChatId = 1L;
        URI url = URI.create("link");
        Link linkToRemove = Link.builder()
            .url(url)
            .build();

        Link link = Link.builder()
            .id(1L)
            .url(url)
            .build();

        when(linkRepository.isLinkExists(linkToRemove)).thenReturn(true);
        when(linkRepository.findLinkByUrl(linkToRemove.getUrl())).thenReturn(link);
        when(linkRepository.isChatLinkRelationshipExists(telegramChatId, link.getId())).thenReturn(false);
        when(linkRepository.areThereAnyChatLinkRelationshipsByLinkId(link.getId())).thenReturn(false);

        LinkResponse result = linkService.removeLink(telegramChatId, url);

        verify(linkRepository, times(1)).removeLink(link);
        verify(additionalInfoService, times(1)).removeAdditionalInfo(link);

        assertNotNull(result);
    }

    @Test
    void removeLink_shouldReturnLinkResponse_whenLinkExists() {
        long telegramChatId = 1L;
        URI url = URI.create("link");
        Link linkToRemove = Link.builder()
            .url(url)
            .build();

        Link link = Link.builder()
            .id(1L)
            .url(url)
            .build();

        LinkResponse excepted = new LinkResponse(link.getId(), link.getUrl());

        when(linkRepository.isLinkExists(linkToRemove)).thenReturn(true);
        when(linkRepository.findLinkByUrl(linkToRemove.getUrl())).thenReturn(link);

        LinkResponse result = linkService.removeLink(telegramChatId, url);

        verify(linkRepository, times(1)).removeLink(link);
        verify(additionalInfoService, times(1)).removeAdditionalInfo(link);

        assertEquals(result, excepted);
    }

    @Test
    void listAllLinks() {
        long telegramChatId = 1L;
        URI firstUrl = URI.create("firstLink");
        URI secondUrl = URI.create("secondLink");

        List<Link> linkList = List.of(
            Link.builder().id(1L).url(firstUrl).build(),
            Link.builder().id(2L).url(secondUrl).build()
        );

        ListLinksResponse excepted = new ListLinksResponse(
            linkList.stream().map(link -> new LinkResponse(link.getId(), link.getUrl())).toList(),
            linkList.size()
        );

        when(linkRepository.findAllLinks(telegramChatId)).thenReturn(linkList);

        ListLinksResponse result = linkService.listAllLinks(1L);

        verify(linkRepository, times(1)).findAllLinks(telegramChatId);
        assertEquals(result, excepted);
    }

    @Test
    void isLinkConnectedToChat() {
        long telegramChatId = 1L;
        URI url = URI.create("link");

        Link linkToCheck = Link.builder()
            .url(url)
            .build();

        Link link = Link.builder()
            .id(1L)
            .url(url)
            .build();

        when(linkRepository.isLinkExists(linkToCheck)).thenReturn(true);
        when(linkRepository.findLinkByUrl(url)).thenReturn(link);

        linkService.isLinkConnectedToChat(telegramChatId, url);

        verify(linkRepository, times(1)).isChatLinkRelationshipExists(telegramChatId, link.getId());
    }

    @Test
    void findLinkByUrl() {
        URI url = URI.create("link");

        linkService.findLinkByUrl(url);

        verify(linkRepository, times(1)).findLinkByUrl(url);
    }

    @Test
    void findLinkById() {
        long linkId = 1L;

        linkService.findLinkById(linkId);

        verify(linkRepository, times(1)).findLinkById(linkId);
    }

    @Test
    void updateLastModifiedTime() {
        long linkId = 1L;
        OffsetDateTime lastModifiedTime = OffsetDateTime.now();

        Link link = Link.builder().id(linkId).lastModifiedTime(lastModifiedTime).build();

        linkService.updateLastModifiedTime(linkId, lastModifiedTime);

        verify(linkRepository, times(1)).updateLinkLastModifiedTime(link);
    }

    @Test
    void updateLastUpdateCheckTime() {
        long linkId = 1L;

        Link link = Link.builder().id(linkId).build();

        linkService.updateLastUpdateCheckTime(linkId);

        verify(linkRepository, times(1)).updateLinkLastUpdateCheckTime(link);
    }
}
