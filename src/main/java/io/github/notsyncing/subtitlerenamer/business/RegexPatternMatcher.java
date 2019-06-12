package io.github.notsyncing.subtitlerenamer.business;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class RegexPatternMatcher extends PatternMatcher {
    private static final HashMap<String, Pattern> patternCache = new HashMap<>();

    private Pattern getPattern(String pattern) {
        synchronized (patternCache) {
            if (patternCache.containsKey(pattern)) {
                return patternCache.get(pattern);
            } else {
                var p = Pattern.compile(pattern);
                patternCache.put(pattern, p);
                return p;
            }
        }
    }

    @Override
    public boolean match(String pattern, String text) {
        return getPattern(pattern).asPredicate().test(text);
    }

    @Override
    public String getNumber(String pattern, String text) {
        var p = getPattern(pattern);
        var m = p.matcher(text);

        if (!m.matches()) {
            return null;
        }

        return m.group("number");
    }
}
