package edu.java.repository.jooq;

import edu.java.domain.GithubRepository;
import edu.java.repository.GithubRepoRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.domain.jooq.tables.GithubRepository.GITHUB_REPOSITORY;

@Repository
public class JooqGithubRepoRepository implements GithubRepoRepository {

    private final DSLContext dslContext;

    public JooqGithubRepoRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public GithubRepository addGithubRepository(GithubRepository githubRepository, Long linkId) {
        return dslContext.insertInto(
                GITHUB_REPOSITORY,
                GITHUB_REPOSITORY.ID,
                GITHUB_REPOSITORY.LINK_ID,
                GITHUB_REPOSITORY.FULL_NAME,
                GITHUB_REPOSITORY.FORKS_COUNT,
                GITHUB_REPOSITORY.UPDATED_AT
            )
            .values(
                githubRepository.getRepositoryId(),
                linkId,
                githubRepository.getFullName(),
                githubRepository.getForksCount(),
                githubRepository.getUpdatedAt()
            )
            .returning(GITHUB_REPOSITORY.fields())
            .fetchOneInto(GithubRepository.class);
    }

    @Override
    @Transactional
    public GithubRepository removeGithubRepository(Long linkId) {
        return dslContext.deleteFrom(GITHUB_REPOSITORY)
            .where(GITHUB_REPOSITORY.LINK_ID.eq(linkId))
            .returning(GITHUB_REPOSITORY.fields())
            .fetchOneInto(GithubRepository.class);
    }

    @Override
    @Transactional
    public GithubRepository findGithubRepositoryByLinkId(long linkId) {
        return dslContext.select(GITHUB_REPOSITORY.fields())
            .from(GITHUB_REPOSITORY)
            .where(GITHUB_REPOSITORY.LINK_ID.eq(linkId))
            .fetchOneInto(GithubRepository.class);
    }
}
