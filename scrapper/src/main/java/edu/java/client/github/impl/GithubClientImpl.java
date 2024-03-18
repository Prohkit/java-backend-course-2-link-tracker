package edu.java.client.github.impl;

import edu.java.client.Client;
import edu.java.client.github.GithubClient;
import edu.java.client.github.dto.RepositoryResponse;
import edu.java.domain.Link;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class GithubClientImpl extends Client implements GithubClient {

    private final WebClient webClient;

    public GithubClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    private static final String SITE_NAME = "github.com";

    @Override
    public RepositoryResponse fetchRepository(String ownerUserName, String repositoryName) {
        return webClient
            .get()
            .uri("/repos/{ownerUserName}/{repositoryName}", ownerUserName, repositoryName)
            .retrieve()
            .bodyToMono(RepositoryResponse.class)
            .block();
    }

    @Override
    public String getUpdateInfo(Link link) {
        String url = link.getUrl().toString();
        String hostName = getHostName(url);
        if (hostName.equals(SITE_NAME)) {
            if (isFetchRepositoryEndpoint(url)) {
                String ownerUserName = getOwnerUserName(url);
                String repositoryName = getRepositoryName(url);
                RepositoryResponse response = fetchRepository(ownerUserName, repositoryName);
                return handleRepositoryResponse(link, response);
            }
        }
        return null;
    }

    private String handleRepositoryResponse(Link link, RepositoryResponse response) {
        if (response.updatedAt().isAfter(link.getLastModifiedTime())) {
            link.setLastModifiedTime(response.updatedAt());
            return "Есть обновление " + link.getUrl().toString();
        }
        return null;
    }

    private Matcher getFetchRepositoryMatcher(String url) {
        Pattern pattern = Pattern.compile("github.com/(?<ownerUserName>\\S+)/(?<repositoryName>\\S+)");
        return pattern.matcher(url);
    }

    private boolean isFetchRepositoryEndpoint(String url) {
        return getFetchRepositoryMatcher(url).find();
    }

    private String getOwnerUserName(String url) {
        Matcher matcher = getFetchRepositoryMatcher(url);
        if (matcher.find()) {
            return matcher.group("ownerUserName");
        }
        return null;
    }

    private String getRepositoryName(String url) {
        Matcher matcher = getFetchRepositoryMatcher(url);
        if (matcher.find()) {
            return matcher.group("repositoryName");
        }
        return null;
    }
}
