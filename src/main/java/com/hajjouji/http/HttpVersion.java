package com.hajjouji.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1", 1, 1);
    public final String LITERAL;
    public final int MAJOR;
    public final int MINOR;

    HttpVersion(String LITERAL, int MAJOR, int MINOR) {
        this.LITERAL = LITERAL;
        this.MAJOR = MAJOR;
        this.MINOR = MINOR;
    }

    private static final Pattern httpVersionPattern = Pattern.compile("^HTTP/(?<major>\\d+)\\.(?<minor>\\d+)");

    public static HttpVersion getCompatibleVersion(String literal) throws BadHttpVersionException {
        Matcher matcher = httpVersionPattern.matcher(literal);
        if (!matcher.find() || matcher.groupCount() != 2) {
            throw new BadHttpVersionException();
        }
        int major = Integer.parseInt(matcher.group("major"));
        int minor = Integer.parseInt(matcher.group("minor"));

        HttpVersion tempCompatible = null;

        for (HttpVersion httpVersion : HttpVersion.values()) {
            if (httpVersion.LITERAL.equals(literal)) {
                return httpVersion;
            } else {
                if (httpVersion.MAJOR == major) {
                    if (httpVersion.MINOR < minor) {
                        tempCompatible = httpVersion;
                    }
                }
            }
        }
        return tempCompatible;
    }
}
