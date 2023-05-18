package com.hajjouji.mapper;

import com.hajjouji.http.HttpMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route {
    private Pattern requestTargetPattern;
    private HttpMethod method;
    private RequestHandler handler;

    public Route(Pattern requestTargetPattern, HttpMethod method, RequestHandler handler) {
        this.requestTargetPattern = requestTargetPattern;
        this.method = method;
        this.handler = handler;
    }

    public boolean isMatching(String requestTarget,HttpMethod requestMethod) {
        Matcher matcher = requestTargetPattern.matcher(requestTarget);
        return matcher.find() && method == requestMethod ;
    }

    public Pattern getRequestTargetPattern() {
        return requestTargetPattern;
    }

    public void setRequestTargetPattern(String requestTargetPattern) {
        this.requestTargetPattern = Pattern.compile(requestTargetPattern);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public RequestHandler getHandler() {
        return handler;
    }

    public void setHandler(RequestHandler handler) {
        this.handler = handler;
    }

}