package edu.java.repository.jpa;

import edu.java.domain.StackOverflowQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaStackOverflowQuestionRepository extends JpaRepository<StackOverflowQuestion, Long> {
    @Transactional
    void deleteStackOverflowQuestionsByLinkId(long linkId);

    @Transactional
    List<StackOverflowQuestion> findStackOverflowQuestionsByLinkId(long linkId);
}
