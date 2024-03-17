package edu.java.client;

import edu.java.domain.Link;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Client {

    public abstract String getUpdateInfo(Link link);

    protected String getHostName(String url) {
        Pattern pattern = Pattern.compile("^(?:https?:\\/\\/)?(?:[^@\\n]+@)?(?:www\\.)?(?<hostName>[^:/\\n?]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group("hostName");
        }
        return null;
    }
}
