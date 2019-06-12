package io.github.notsyncing.subtitlerenamer.business;

import java.nio.file.Paths;
import java.util.regex.Pattern;

public class SimplePatternMatcher extends PatternMatcher {
    private RegexPatternMatcher regexMatcher = new RegexPatternMatcher();

    private String makeRegex(String pattern) {
        var n = "<number>";
        var i = pattern.indexOf(n);

        if (i < 0) {
            return Pattern.quote(pattern);
        }

        var a = pattern.substring(0, i);
        var b = pattern.substring(i + n.length());

        return Pattern.quote(a) + "(?<number>\\d+)" + Pattern.quote(b);
    }

    @Override
    public boolean match(String pattern, String text) {
        return regexMatcher.match(makeRegex(pattern), text);
    }

    @Override
    public String getNumber(String pattern, String text) {
        return regexMatcher.getNumber(makeRegex(pattern), text);
    }
}
