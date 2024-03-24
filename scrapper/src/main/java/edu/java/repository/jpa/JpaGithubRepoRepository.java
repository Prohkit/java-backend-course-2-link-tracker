package edu.java.repository.jpa;

import edu.java.domain.GithubRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaGithubRepoRepository extends JpaRepository<GithubRepository, Long> {
    @Transactional
    void deleteGithubRepositoryByLinkId(long linkId);

    @Transactional
    GithubRepository findGithubRepositoryByLinkId(long linkId);
}
