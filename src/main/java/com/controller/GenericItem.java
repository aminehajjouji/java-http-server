package com.controller;

import com.http.HttpStatusCode;
import com.http.HttpVersion;

import java.util.Map;

public class GenericItem<T> {
    private HttpStatusCode statusCode;
    private Map<String, String> headers;
    private T body;

}
