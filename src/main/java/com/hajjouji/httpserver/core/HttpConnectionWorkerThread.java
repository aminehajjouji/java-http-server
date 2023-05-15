package com.hajjouji.httpserver.core;

import com.hajjouji.http.HttpParser;
import com.hajjouji.http.HttpParsingException;
import com.hajjouji.http.HttpRequest;
import com.hajjouji.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    private Socket socket;
    private HttpParser httpParser;

    HttpConnectionWorkerThread(Socket socket, HttpParser httpParser) {
        this.socket = socket;
        this.httpParser = httpParser;
    }

    @Override
    public void run() {

        try {
            /*ips = socket.getInputStream();
            ops = socket.getOutputStream();*/
            handlRequest();
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
            e.printStackTrace();
        } catch (HttpParsingException e) {
            throw new RuntimeException(e);
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

    private void handlRequest() throws HttpParsingException, IOException {
        HttpRequest httpRequest = httpParser.parseHttpRequest(socket.getInputStream());
        String html = "<html><head><title>My test Page</title></head><body><h1>Hello from my htttp server!</h1></body></html>";
        final String CRLF = "\r\n";//13,10 code ascii
        String response = "HTTP/1.1 200 ok" + CRLF + //status line : http version response code response message
                "Content-Length: " + html.getBytes().length + CRLF + //header
                CRLF +
                html +
                CRLF + CRLF;
        socket.getOutputStream().write(response.getBytes());
        LOGGER.info("CONNECTION PROCESSING FINISHED ");
    }
}
