package com.http;

public enum HttpMethod {
    GET,HEAD,POST,PUT,DELETE,CONNECT,OPTIONS,TRACE,PATCH;
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