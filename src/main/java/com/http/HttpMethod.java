package com.http;

public enum HttpMethod {
    GET,HEAD;
    public static final int MAX_LENGTH ;
    static {
        int tempMaxLength =-1;
        for (HttpMethod httpMethod : values()) {
            if (httpMethod.name().length() > tempMaxLength) {
                tempMaxLength = httpMethod.name().length();
            }
        }
        MAX_LENGTH = tempMaxLength;
    }
}