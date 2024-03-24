package edu.java.configuration;

import edu.java.client.github.GithubClient;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.repository.jpa.JpaGithubRepoRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.repository.jpa.JpaStackOverflowQuestionRepository;
import edu.java.repository.jpa.JpaTelegramChatRepository;
import edu.java.service.AdditionalInfoService;
import edu.java.service.GithubRepoService;
import edu.java.service.LinkService;
import edu.java.service.StackOverflowQuestionService;
import edu.java.service.TelegramChatService;
import edu.java.service.jpa.JpaAdditionalInfoService;
import edu.java.service.jpa.JpaGithubRepoService;
import edu.java.service.jpa.JpaLinkService;
import edu.java.service.jpa.JpaStackOverflowQuestionService;
import edu.java.service.jpa.JpaTelegramChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public AdditionalInfoService additionalInfoService(
        GithubRepoService githubRepoService,
        GithubClient githubClient,
        StackOverflowQuestionService stackOverflowQuestionService,
        StackOverflowClient stackOverflowClient
    ) {
        return new JpaAdditionalInfoService(
            githubRepoService,
            githubClient,
            stackOverflowQuestionService,
            stackOverflowClient
        );
    }

    @Bean
    public GithubRepoService githubRepoService(JpaGithubRepoRepository githubRepoRepository) {
        return new JpaGithubRepoService(githubRepoRepository);
    }

    @Bean
    public LinkService linkService(
        JpaLinkRepository linkRepository,
        AdditionalInfoService additionalInfoService
    ) {
        return new JpaLinkService(linkRepository, additionalInfoService);
    }

    @Bean
    public StackOverflowQuestionService stackOverflowQuestionService(
        JpaStackOverflowQuestionRepository stackOverflowQuestionRepository
    ) {
        return new JpaStackOverflowQuestionService(stackOverflowQuestionRepository);
    }

    @Bean
    public TelegramChatService telegramChatService(
        JpaTelegramChatRepository telegramChatRepository,
        JpaLinkRepository linkRepository,
        AdditionalInfoService additionalInfoService
    ) {
        return new JpaTelegramChatService(telegramChatRepository, linkRepository, additionalInfoService);
    }
}
