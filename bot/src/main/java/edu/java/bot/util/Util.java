package edu.java.bot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private Util() {
    }

    public static boolean isUrlValid(String url) {
        Pattern pattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
        // в будущем проверка будет происходить по реализуемым ресурсам
    }
}
