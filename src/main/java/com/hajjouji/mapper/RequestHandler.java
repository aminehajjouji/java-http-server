package com.hajjouji.mapper;

import com.hajjouji.http.HttpRequest;
import com.hajjouji.http.ResponseEntity;

@FunctionalInterface
public interface RequestHandler {
    ResponseEntity<?> handel(HttpRequest request);
}

