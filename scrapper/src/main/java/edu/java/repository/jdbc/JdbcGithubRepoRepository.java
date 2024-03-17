package edu.java.repository.jdbc;

import edu.java.domain.GithubRepository;
import edu.java.repository.GithubRepoRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcGithubRepoRepository implements GithubRepoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcGithubRepoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public GithubRepository addGithubRepository(GithubRepository githubRepository, Long linkId) {
        String insertSql = "insert into github_repository (id, link_id, full_name, forks_count, updated_at) "
            + "VALUES (?, ?, ?, ?, ?) returning *";

        return jdbcTemplate.queryForObject(
            insertSql,
            new BeanPropertyRowMapper<>(GithubRepository.class),
            githubRepository.getRepositoryId(),
            linkId,
            githubRepository.getFullName(),
            githubRepository.getForksCount(),
            githubRepository.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public GithubRepository removeGithubRepository(Long linkId) {
        return jdbcTemplate.queryForObject(
            "delete from github_repository where link_id = ? returning *",
            new BeanPropertyRowMapper<>(GithubRepository.class),
            linkId
        );
    }

    @Override
    @Transactional
    public GithubRepository findGithubRepositoryByLinkId(long linkId) {
        return jdbcTemplate.queryForObject(
            "select * from github_repository where link_id = ?",
            new BeanPropertyRowMapper<>(GithubRepository.class),
            linkId
        );
    }
}
