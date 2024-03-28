package edu.java.repository.jpa;

import edu.java.domain.Link;
import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {

    @Transactional
    @Query(value = "select exists (select true from link where url = :url)", nativeQuery = true)
    boolean existsLinkByUrl(@Param("url") String url);

    @Transactional
    Link findLinkByUrl(URI url);

    @Transactional
    @Query(value = "select exists (select true from chat_link where chat_id = :chatId and link_id = :linkId)",
           nativeQuery = true)
    boolean isChatLinkRelationshipExists(@Param("chatId") long telegramId, @Param("linkId") long linkId);

    @Transactional
    @Modifying
    @Query(value = "insert into chat_link (chat_id, link_id) values (:chatId, :linkId)", nativeQuery = true)
    void addChatLinkRelationship(@Param("chatId") long telegramId, @Param("linkId") long linkId);

    @Transactional
    @Modifying
    @Query(value = "delete from chat_link where chat_id = :chatId and link_id = :linkId", nativeQuery = true)
    void removeChatLinkRelationship(@Param("chatId") long telegramId, @Param("linkId") long linkId);

    @Transactional
    @Query(value = "select exists (select true from chat_link where link_id = :linkId)", nativeQuery = true)
    boolean areThereAnyChatLinkRelationshipsByLinkId(@Param("linkId") long linkId);

    @Transactional
    @Query(value = "select * from link left join chat_link cl on link.id = cl.link_id where cl.chat_id = :chatId",
           nativeQuery = true)
    List<Link> findLinksByChatId(@Param("chatId") long telegramId);

    @Transactional
    @Query(value = "select * from link where link.last_update_check_time < :lastUpdateCheckTime", nativeQuery = true)
    List<Link> findLinksToCheck(@Param("lastUpdateCheckTime") Timestamp timeToCheck);

    @Transactional
    @Modifying
    @Query(value = "update link set last_modified_time = :lastModifiedTime where id = :linkId", nativeQuery = true)
    void updateLinkLastModifiedTime(
        @Param("linkId") long linkId,
        @Param("lastModifiedTime") OffsetDateTime lastModifiedTime
    );

    @Transactional
    @Modifying
    @Query(value = "update link set last_update_check_time = now() where id = :linkId", nativeQuery = true)
    void updateLinkLastUpdateCheckTime(@Param("linkId") long linkId);
}
