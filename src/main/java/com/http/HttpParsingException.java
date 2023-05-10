package com.http;

public class HttpParsingException extends Exception {
    private HttpStatusCode erroCode;

    public HttpParsingException(HttpStatusCode erroCode) {
        super(erroCode.MESSAGE);
        this.erroCode = erroCode;
    }

    public HttpStatusCode getErroCode() {
        return erroCode;
    }
}
