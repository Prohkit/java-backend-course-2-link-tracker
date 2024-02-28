package edu.java.client.stackoverflow.impl;

import edu.java.client.Client;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.client.stackoverflow.dto.QuestionResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class StackOverflowClientImpl extends Client implements StackOverflowClient {

    private final WebClient webClient;

    private static final String SITE_NAME = "stackoverflow.com";

    public StackOverflowClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public QuestionResponse fetchQuestion(Long questionId) {
        return webClient
            .get()
            .uri("questions/{questionId}/?site=stackoverflow&filter=withbody", questionId)
            .retrieve()
            .bodyToMono(QuestionResponse.class)
            .block();
    }

    @Override
    public boolean getUpdateInfo(String url) {
        String hostName = getHostName(url);
        if (hostName.equals(SITE_NAME)) {
            if (isQuestionByIdEndpoint(url)) {
                Long questionId = getQuestionId(url);
                QuestionResponse response = fetchQuestion(questionId);
            }
            return true;
        }
        return true;
    }

    private Matcher getQuestionByIdMatcher(String url) {
        Pattern pattern = Pattern.compile("stackoverflow.com/questions/(?<questionId>\\d+)");
        return pattern.matcher(url);
    }

    private boolean isQuestionByIdEndpoint(String url) {
        return getQuestionByIdMatcher(url).find();
    }

    private Long getQuestionId(String url) {
        Matcher matcher = getQuestionByIdMatcher(url);
        if (matcher.find()) {
            return Long.parseLong(matcher.group("questionId"));
        }
        return null;
    }
}
