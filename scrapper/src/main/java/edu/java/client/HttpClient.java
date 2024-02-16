package edu.java.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class HttpClient {
    private HttpClient next;

    protected String clientHostName;

    public static HttpClient link(HttpClient first, HttpClient... chain) {
        HttpClient head = first;
        for (HttpClient nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean getUpdateInfo(String url);

    protected boolean handleNext(String url) {
        if (next == null) {
            return true;
        }
        return next.getUpdateInfo(url);
    }

    protected String getHostName(String url) {
        Pattern pattern = Pattern.compile("^(?:https?:\\/\\/)?(?:[^@\\n]+@)?(?:www\\.)?(?<hostName>[^:/\\n?]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group("hostName");
        }
        return null;
    }
}
