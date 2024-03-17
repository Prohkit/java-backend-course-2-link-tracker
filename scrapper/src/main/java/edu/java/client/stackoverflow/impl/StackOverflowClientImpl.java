package edu.java.client.stackoverflow.impl;

import edu.java.client.Client;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.client.stackoverflow.dto.QuestionResponse;
import edu.java.domain.Link;
import edu.java.domain.StackOverflowQuestion;
import edu.java.service.StackOverflowQuestionService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@SuppressWarnings("MultipleStringLiterals")
public class StackOverflowClientImpl extends Client implements StackOverflowClient {

    private final WebClient webClient;

    private final StackOverflowQuestionService stackOverflowQuestionService;

    public StackOverflowClientImpl(WebClient webClient, StackOverflowQuestionService stackOverflowQuestionService) {
        this.webClient = webClient;
        this.stackOverflowQuestionService = stackOverflowQuestionService;
    }

    private static final String SITE_NAME = "stackoverflow.com";

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
    public String getUpdateInfo(Link link) {
        String url = link.getUrl().toString();
        String hostName = getHostName(url);
        if (hostName.equals(SITE_NAME)) {
            if (isQuestionByIdEndpoint(url)) {
                Long questionId = getQuestionId(url);
                QuestionResponse response = fetchQuestion(questionId);
                return handleQuestionResponse(link, response);
            }
        }
        return null;
    }

    private String handleQuestionResponse(Link link, QuestionResponse response) {
        QuestionResponse.ItemResponse item = response.items().get(0);
        if (item.lastActivityDate().isAfter(link.getLastModifiedTime())) {
            link.setLastModifiedTime(item.lastActivityDate());
            StackOverflowQuestion question =
                stackOverflowQuestionService.getStackOverflowQuestionByLinkId(link.getId()).get(0);
            return getDescription(link.getUrl().toString(), question, item);
        }
        return null;
    }

    private String getDescription(String url, StackOverflowQuestion question, QuestionResponse.ItemResponse item) {
        StringBuilder stringBuilder = new StringBuilder("Есть обновление ")
            .append(url).append(".").append(System.lineSeparator());
        if (!item.score().equals(question.getScore())) {
            stringBuilder.append("Изменилось количество очков, было ").append(question.getScore())
                .append(", стало ").append(item.score()).append(".").append(System.lineSeparator());
        }
        if (!item.answerCount().equals(question.getAnswerCount())) {
            stringBuilder.append("Изменилось количество ответов по вопросу, было")
                .append(question.getAnswerCount()).append(", стало ")
                .append(item.answerCount()).append(System.lineSeparator());
        }
        if (item.isAnswered() != question.isAnswered()) {
            stringBuilder.append("Изменился статус ответа на вопрос.").append(System.lineSeparator());
        }
        return new String(stringBuilder);
    }

    private Matcher getQuestionByIdMatcher(String url) {
        Pattern pattern = Pattern.compile("stackoverflow.com/questions/(?<questionId>\\d+)");
        return pattern.matcher(url);
    }

    private boolean isQuestionByIdEndpoint(String url) {
        return getQuestionByIdMatcher(url).find();
    }

    public Long getQuestionId(String url) {
        Matcher matcher = getQuestionByIdMatcher(url);
        if (matcher.find()) {
            return Long.parseLong(matcher.group("questionId"));
        }
        return null;
    }
}
