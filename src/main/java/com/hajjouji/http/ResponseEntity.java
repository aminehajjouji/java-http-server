package com.hajjouji.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.hajjouji.httpserver.util.Json;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntity<T> {
    private HttpStatusCode statusCode;
    private Map<String, String> headers = new HashMap<>();
    private T responseBody;

    public static <A> ResponseEntity<A> builder(HttpStatusCode statusCode,A body) {
        ResponseEntity<A> responseEntity = new ResponseEntity<>();
        responseEntity.setStatusCode(statusCode);
        responseEntity.setResponseBody(body);
        return responseEntity;
    }
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String stringifiedResponseBody() throws JsonProcessingException {
        JsonNode objectToNode = Json.toJson(responseBody);
        return Json.stringifyPretty(objectToNode);
    }
}