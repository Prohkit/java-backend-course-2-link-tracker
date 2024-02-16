package edu.java.client;

import edu.java.response.QuestionResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackoverflowClientImpl extends HttpClient implements StackoverflowClient {

    public StackoverflowClientImpl() {
        clientHostName = "stackoverflow.com";
    }

    @Override
    public QuestionResponse fetchQuestion(String questionId) {
        return null;
    }

    @Override
    public boolean getUpdateInfo(String url) {
        String hostName = getHostName(url);
        if (!hostName.isEmpty() && hostName.equals(clientHostName)) {
            Long questionId = getQuestionId(url);
            String questionResponse = getQuestionResponse(questionId);
        }
        return handleNext(url);
    }

    private Long getQuestionId(String url) {
        Pattern pattern = Pattern.compile("stackoverflow.com/questions/(?<questionId>\\d+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return Long.parseLong(matcher.group("questionId"));
        }
        return null;
    }

    private String getQuestionResponse(Long questionId) {
        WebClient client = WebClient.create("https://api.stackexchange.com/2.2/");
        return client
            .get()
            .uri("questions/" + questionId + "/?site=stackoverflow.com")
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
