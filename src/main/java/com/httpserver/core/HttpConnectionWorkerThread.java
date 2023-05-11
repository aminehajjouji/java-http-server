package com.httpserver.core;

import com.http.HttpParser;
import com.http.HttpParsingException;
import com.http.HttpRequest;
import com.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        InputStream ips = null;
        OutputStream ops = null;
        try {
            ips = socket.getInputStream();
            ops = socket.getOutputStream();

            // this bloc of code print request info
          /*  int _byte = ips.read();
            while ((_byte = ips.read()) >= 0) {
                System.out.print((char) _byte);
            }*/
           // HttpRequest httpRequest = httpParser.parseHttpRequest(ips);
            String html = "<html><head><title>My test Page</title></head><body><h1>Hello from my htttp server!</h1></body></html>";
            final String CRLF = "\r\n";//13,10 code ascii
            String response = "HTTP/1.1 200 ok" + CRLF + //status line : http version response code response message
                    "Content-Length: " + html.getBytes().length + CRLF + //header
                    CRLF +
                    html +
                    CRLF + CRLF;
            ops.write(response.getBytes());
            LOGGER.info("CONNECTION PROCESSING FINISHED");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
            e.printStackTrace();
        } finally {
            if (ips != null) {
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ops != null) {
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
