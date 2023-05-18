package com.hajjouji.httpserver.core;

import com.hajjouji.http.*;
import com.hajjouji.mapper.Middleware;
import com.hajjouji.parsers.RequestParser;
import com.hajjouji.httpserver.HttpServer;
import com.hajjouji.parsers.ResponseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    private final Middleware middleware = new Middleware();

    private Socket socket;
    private RequestParser requestParser;

    HttpConnectionWorkerThread(Socket socket, RequestParser requestParser) {
        this.socket = socket;
        this.requestParser = requestParser;
    }

    @Override
    public void run() {

        try {
            handelRequest();
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void handelRequest() throws IOException {
        try {
            HttpRequest request = requestParser.parseHttpRequest(socket.getInputStream());
            ResponseEntity<?> response = middleware.findRequestedRoute(request).getHandler().handel(request);
            sendResponseWhenSuccess(request, response);
        } catch (HttpParsingException exception) {
            sendResponseWhenFail(exception.getErroCode(), exception.getMessage());
        } catch (RuntimeException e) {
            sendResponseWhenFail(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR, "Server error");
        }
    }

    private void sendResponseWhenSuccess(HttpRequest request, ResponseEntity<?> responseEntity) throws IOException {
        HttpResponse response = new HttpResponse();
        response.setHttpVersion(request.getHttpVersion());
        response.setStatusCode(responseEntity.getStatusCode().STATUS_CODE);
        response.setStatusLiteral(responseEntity.getStatusCode().MESSAGE);
        response.setHeader(responseEntity.getHeaders());
        response.setBody(responseEntity.stringifiedResponseBody());
        ResponseParser.parseHttpResponse(response, socket.getOutputStream());
    }

    private void sendResponseWhenFail(HttpStatusCode status, String errMsg) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Error-Message", errMsg);
        HttpResponse response = new HttpResponse();
        response.setHttpVersion(HttpVersion.HTTP_1_1);
        response.setStatusCode(status.STATUS_CODE);
        response.setStatusLiteral(status.MESSAGE);
        response.setHeader(headers);
        ResponseParser.parseHttpResponse(response, socket.getOutputStream());
    }

}