package edu.java.repository.jdbc;

import edu.java.domain.GithubRepository;
import edu.java.domain.Link;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class JdbcGithubRepoRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcGithubRepoRepository githubRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addGithubRepository() {
        GithubRepository githubRepositoryToAdd = GithubRepository.builder()
            .repositoryId(1L)
            .fullName("TestRepository")
            .forksCount(0)
            .updatedAt(OffsetDateTime.now())
            .build();

        Link insertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "link"
        );

        GithubRepository addedGithubRepository =
            githubRepository.addGithubRepository(githubRepositoryToAdd, insertedLink.getId());
        GithubRepository githubRepositoryFromDatabase = jdbcTemplate.queryForObject(
            "select * from github_repository where repository_id = ?",
            new BeanPropertyRowMapper<>(GithubRepository.class),
            addedGithubRepository.getRepositoryId()
        );

        assertEquals(addedGithubRepository, githubRepositoryFromDatabase);
    }

    @Test
    @Transactional
    @Rollback
    void removeGithubRepository() {
        GithubRepository githubRepositoryToRemove = GithubRepository.builder()
            .repositoryId(1L)
            .fullName("TestRepository")
            .forksCount(0)
            .updatedAt(OffsetDateTime.now())
            .build();

        Link insertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "link"
        );

        GithubRepository insertedGithubRepository = jdbcTemplate.queryForObject(
            "insert into github_repository (repository_id, link_id, full_name, forks_count, updated_at) "
                + "values (?, ?, ?, ?, ?) returning *",
            new BeanPropertyRowMapper<>(GithubRepository.class),
            githubRepositoryToRemove.getRepositoryId(),
            insertedLink.getId(),
            githubRepositoryToRemove.getFullName(),
            githubRepositoryToRemove.getForksCount(),
            githubRepositoryToRemove.getUpdatedAt()
        );

        GithubRepository removedGithubRepository = githubRepository.removeGithubRepository(insertedLink.getId());

        assertEquals(insertedGithubRepository, removedGithubRepository);
    }

    @Test
    @Transactional
    @Rollback
    void findGithubRepositoryByLinkId() {
        GithubRepository githubRepository = GithubRepository.builder()
            .repositoryId(1L)
            .fullName("TestRepository")
            .forksCount(0)
            .updatedAt(OffsetDateTime.now())
            .build();

        Link insertedLink = jdbcTemplate.queryForObject(
            "insert into link (url) values (?) returning *",
            new BeanPropertyRowMapper<>(Link.class),
            "link"
        );

        GithubRepository insertedGithubRepository = jdbcTemplate.queryForObject(
            "insert into github_repository (repository_id, link_id, full_name, forks_count, updated_at) "
                + "values (?, ?, ?, ?, ?) returning *",
            new BeanPropertyRowMapper<>(GithubRepository.class),
            githubRepository.getRepositoryId(),
            insertedLink.getId(),
            githubRepository.getFullName(),
            githubRepository.getForksCount(),
            githubRepository.getUpdatedAt()
        );

        GithubRepository githubRepositoryFromDatabase =
            this.githubRepository.findGithubRepositoryByLinkId(insertedLink.getId());

        assertEquals(githubRepositoryFromDatabase, insertedGithubRepository);
    }
}
