package edu.java.service;

import edu.java.domain.Link;
import edu.java.dto.scrapper.response.LinkResponse;
import edu.java.dto.scrapper.response.ListLinksResponse;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {
    LinkResponse addLink(long telegramChatId, URI url);

    LinkResponse removeLink(long telegramChatId, URI url);

    ListLinksResponse listAllLinks(long telegramChatId);

    boolean isLinkConnectedToChat(long telegramChat, URI url);

    List<Link> findLinksToCheck(Duration interval);

    Link findLinkByUrl(URI url);

    Link findLinkById(Long id);

    void updateLastModifiedTime(long id, OffsetDateTime lastModifiedTime);

    void updateLastUpdateCheckTime(long id);
}
