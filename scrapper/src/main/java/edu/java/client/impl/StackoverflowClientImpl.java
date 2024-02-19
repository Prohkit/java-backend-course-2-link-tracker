package edu.java.client.impl;

import edu.java.client.Client;
import edu.java.client.StackoverflowClient;
import edu.java.response.QuestionResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class StackoverflowClientImpl extends Client implements StackoverflowClient {

    @Value("${url.site.stackoverflow}")
    private String clientHostName;

    @Value("${url.api.stackoverflow}")
    private String apiUrl;

    @Override
    public QuestionResponse fetchQuestion(Long questionId) {
        WebClient client = WebClient.create(apiUrl);
        return client
            .get()
            .uri("questions/" + questionId + "/?site=stackoverflow&filter=withbody")
            .retrieve()
            .bodyToMono(QuestionResponse.class)
            .block();
    }

    @Override
    public boolean getUpdateInfo(String url) {
        String hostName = getHostName(url);
        if (!hostName.isEmpty() && hostName.equals(clientHostName)) {
            if (isQuestionByIdEndpoint(url)) {
                Long questionId = getQuestionId(url);
                fetchQuestion(questionId);
            }
            return true;
        }
        return handleNext(url);
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
