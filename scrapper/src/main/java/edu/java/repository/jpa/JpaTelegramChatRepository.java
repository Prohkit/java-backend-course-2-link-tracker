package edu.java.repository.jpa;

import edu.java.domain.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaTelegramChatRepository extends JpaRepository<Chat, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from chat_link where chat_id = :linkId returning link_id", nativeQuery = true)
    List<Long> removeChatLinkRelationships(@Param("linkId") long linkId);

    @Transactional
    @Query(value = "select chat_id from chat_link where link_id = :linkId", nativeQuery = true)
    List<Long> getTelegramChatIdsByLinkId(@Param("linkId") long linkId);
}
