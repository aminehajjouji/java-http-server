package com.hajjouji.controller;

import com.hajjouji.http.HttpStatusCode;

import java.util.Map;

public class GenericItem<T> {
    private HttpStatusCode statusCode;
    private Map<String, String> headers;
    private T body;

}
