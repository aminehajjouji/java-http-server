package com.hajjouji.parsers;

import com.hajjouji.http.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResponseParser {
    private static final String CRLF = "\r\n";
    private static final String SP = " ";
    private static final String H_DELIMITER = ": ";

    private ResponseParser() {
        throw new IllegalStateException("Utility class should not be initialized");
    }

    public static void parseHttpResponse(HttpResponse response, OutputStream outputStream) throws IOException {

        StringBuilder responseWriter = new StringBuilder();
        // Status line
        responseWriter.append(response.getHttpVersionLiteral()).append(SP)
                .append(response.getStatusCode()).append(SP)
                .append(response.getStatusLiteral()).append(CRLF);

        // headers
        for (Map.Entry<String,String> header : response.getHeaders().entrySet()) {
            responseWriter.append(header.getKey()).append(H_DELIMITER).append(header.getValue()).append(CRLF);
        }
        responseWriter.append(CRLF);

        // body
        if (!response.getBody().isBlank()) {
            responseWriter.append(response.getBody()).append(CRLF);
        }

        outputStream.write(responseWriter.toString().getBytes());
    }
}