package com.hajjouji.mapper;

import com.hajjouji.http.HttpParsingException;
import com.hajjouji.http.HttpRequest;
import com.hajjouji.http.HttpStatusCode;

public class Middleware {
    public Route findRequestedRoute(HttpRequest request) throws HttpParsingException {
        for (Route rote : Mapper.routes) {
            if (rote.isMatching(request.getRequestTarget(),request.getMethod())) {
                return rote;
            }
        }
        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND);
    }
}
