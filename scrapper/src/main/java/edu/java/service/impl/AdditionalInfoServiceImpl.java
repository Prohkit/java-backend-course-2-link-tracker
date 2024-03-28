package edu.java.service.impl;

import edu.java.client.github.GithubClient;
import edu.java.client.github.dto.RepositoryResponse;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.client.stackoverflow.dto.QuestionResponse;
import edu.java.domain.Link;
import edu.java.service.AdditionalInfoService;
import edu.java.service.GithubRepoService;
import edu.java.service.StackOverflowQuestionService;

public class AdditionalInfoServiceImpl implements AdditionalInfoService {

    private final GithubRepoService githubService;

    private final GithubClient githubClient;

    private final StackOverflowQuestionService stackOverflowService;

    private final StackOverflowClient stackOverflowClient;

    private final String github = "github";

    private final String stackoverflow = "stackoverflow";

    public AdditionalInfoServiceImpl(
        GithubRepoService githubService,
        GithubClient githubClient,
        StackOverflowQuestionService stackOverflowService,
        StackOverflowClient stackOverflowClient
    ) {
        this.githubService = githubService;
        this.githubClient = githubClient;
        this.stackOverflowService = stackOverflowService;
        this.stackOverflowClient = stackOverflowClient;
    }

    @Override
    public void removeAdditionalInfo(Link link) {
        if (link.getUrl().toString().contains(github)) {
            githubService.removeGithubRepository(link.getId());
        } else if (link.getUrl().toString().contains(stackoverflow)) {
            stackOverflowService.removeStackOverflowQuestion(link.getId());
        }
    }

    @Override
    public void addAdditionalInfo(Link link) {
        if (link.getUrl().toString().contains(github)) {
            addGithubRepository(link);
        } else if (link.getUrl().toString().contains(stackoverflow)) {
            addStackOverflowQuestion(link);
        }
    }

    private void addGithubRepository(Link link) {
        String githubOwnerUserName = githubClient.getOwnerUserName(link.getUrl().toString());
        String githubRepositoryName = githubClient.getRepositoryName(link.getUrl().toString());
        RepositoryResponse repositoryResponse =
            githubClient.fetchRepository(githubOwnerUserName, githubRepositoryName);
        githubService.addGithubRepository(repositoryResponse, link.getId());
    }

    private void addStackOverflowQuestion(Link link) {
        Long questionId = stackOverflowClient.getQuestionId(link.getUrl().toString());
        QuestionResponse questionResponse = stackOverflowClient.fetchQuestion(questionId);
        stackOverflowService.addStackOverflowQuestion(questionResponse, link.getId());
    }
}
