package com.hajjouji.http;

public class HttpResponse extends HttpMessage {
    /** 200, 201, 400, 500  **/
    private int statusCode;
    /** OK, Created, Not found, Internal Server Error **/
    private String statusLiteral;
    private String errorMessage;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusLiteral() {
        return statusLiteral;
    }

    public void setStatusLiteral(String statusLiteral) {
        this.statusLiteral = statusLiteral;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getHttpVersionLiteral() {
        return getHttpVersion().LITERAL;
    }
}