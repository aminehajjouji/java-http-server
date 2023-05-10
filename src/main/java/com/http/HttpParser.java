package com.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    public HttpRequest parseHttpRequest(InputStream ips) {
        InputStreamReader reader = new InputStreamReader(ips, StandardCharsets.US_ASCII);
        HttpRequest request = new HttpRequest();
        parseRequestLine(reader, request);
        parseHeader(reader, request);
        parseBody(reader, request);
        return request;
    }


    private void parseRequestLine(InputStreamReader ips, HttpRequest request) {
    }

    private void parseHeader(InputStreamReader ips, HttpRequest request) {
    }

    private void parseBody(InputStreamReader ips, HttpRequest request) {
    }

}
