package edu.java.repository;

import edu.java.domain.Link;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

public interface LinkRepository {
    Link addLink(Link link);

    void addChatLinkRelationship(long telegramChatId, long linkId);

    boolean isChatLinkRelationshipExists(long telegramChatId, long linkId);

    void removeChatLinkRelationship(long telegramChatId, long linkId);

    boolean areThereAnyChatLinkRelationshipsByLinkId(Long linkId);

    Link removeLink(Link link);

    List<Link> findAllLinks(long telegramChatId);

    boolean isLinkExists(Link link);

    List<Link> findLinksToCheck(Timestamp sqlTimestamp);

    Link findLinkByUrl(URI url);

    Link findLinkById(long linkId);

    void updateLinkLastModifiedTime(Link link);

    void updateLinkLastUpdateCheckTime(Link link);
}
