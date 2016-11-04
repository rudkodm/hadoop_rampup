package by.rudko.hru.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextUtils {
    private TextUtils() {
    }

    public static List<String> wordsInText(String s) {
        Pattern p = Pattern.compile("\\w+");
        Matcher m = p.matcher(s);
        List<String> result = new ArrayList<>();
        while(m.find()) {
            result.add(m.group());
        }
        return result;
    }
}
