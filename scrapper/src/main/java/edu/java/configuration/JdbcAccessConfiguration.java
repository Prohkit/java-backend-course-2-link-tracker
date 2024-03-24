package edu.java.configuration;

import edu.java.client.github.GithubClient;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.repository.jdbc.JdbcGithubRepoRepository;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcStackOverflowQuestionRepository;
import edu.java.repository.jdbc.JdbcTelegramChatRepository;
import edu.java.service.AdditionalInfoService;
import edu.java.service.GithubRepoService;
import edu.java.service.LinkService;
import edu.java.service.StackOverflowQuestionService;
import edu.java.service.TelegramChatService;
import edu.java.service.impl.AdditionalInfoServiceImpl;
import edu.java.service.impl.GithubRepoServiceImpl;
import edu.java.service.impl.LinkServiceImpl;
import edu.java.service.impl.StackOverflowQuestionServiceImpl;
import edu.java.service.impl.TelegramChatServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public AdditionalInfoService additionalInfoService(
        GithubRepoService githubRepoService,
        GithubClient githubClient,
        StackOverflowQuestionService stackOverflowQuestionService,
        StackOverflowClient stackOverflowClient
    ) {
        return new AdditionalInfoServiceImpl(
            githubRepoService,
            githubClient,
            stackOverflowQuestionService,
            stackOverflowClient
        );
    }

    @Bean
    public GithubRepoService githubRepoService(JdbcGithubRepoRepository githubRepoRepository) {
        return new GithubRepoServiceImpl(githubRepoRepository);
    }

    @Bean
    public LinkService linkService(
        JdbcLinkRepository linkRepository,
        AdditionalInfoService additionalInfoService
    ) {
        return new LinkServiceImpl(linkRepository, additionalInfoService);
    }

    @Bean
    public StackOverflowQuestionService stackOverflowQuestionService(
        JdbcStackOverflowQuestionRepository stackOverflowQuestionRepository
    ) {
        return new StackOverflowQuestionServiceImpl(stackOverflowQuestionRepository);
    }

    @Bean
    public TelegramChatService telegramChatService(
        JdbcTelegramChatRepository telegramChatRepository,
        JdbcLinkRepository linkRepository,
        AdditionalInfoService additionalInfoService
    ) {
        return new TelegramChatServiceImpl(telegramChatRepository, linkRepository, additionalInfoService);
    }
}
