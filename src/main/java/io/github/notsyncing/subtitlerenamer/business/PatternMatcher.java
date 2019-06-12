package io.github.notsyncing.subtitlerenamer.business;

import io.github.notsyncing.subtitlerenamer.constants.PatternType;

import java.security.InvalidParameterException;

public abstract class PatternMatcher {
    public static PatternMatcher create(PatternType type) {
        switch (type) {
            case Simple:
                return new SimplePatternMatcher();
            case Regex:
                return new RegexPatternMatcher();
            default:
                throw new InvalidParameterException("Unsupported pattern type " + type);
        }
    }

    public abstract boolean match(String pattern, String text);

    public abstract String getNumber(String pattern, String text);
}
