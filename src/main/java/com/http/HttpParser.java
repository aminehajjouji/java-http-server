package com.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int sp = 0x20;
    private static final int cr = 0x0D;
    private static final int lf = 0x0A;

    public HttpRequest parseHttpRequest(InputStream ips) throws HttpParsingException {
        // InputStreamReader reader = new InputStreamReader(ips, StandardCharsets.US_ASCII);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ips, StandardCharsets.US_ASCII));
        HttpRequest request = new HttpRequest();
        try {
            parseRequestLine(reader, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseHeader(reader, request);
        parseBody(reader, request);
        return request;
    }

    // Old Solution using multiple test cases
    /*private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        int _byte;
        StringBuilder processingDataBuffer = new StringBuilder();
        boolean methodParsed = Boolean.FALSE;
        boolean requestTargetParsed = Boolean.FALSE;
        while ((_byte = reader.read()) >= 0) {
            if (_byte == cr) {
                _byte = reader.read();
                if (_byte == lf) {
                    LOGGER.debug("Request line version to Process : {}", processingDataBuffer.toString());
                    return;
                }
            }
            if (_byte == sp) {
                if (!methodParsed) {
                    LOGGER.debug("Request line Method to Process: {}", processingDataBuffer.toString());
                    request.setMethod(processingDataBuffer.toString());
                    methodParsed = Boolean.TRUE;
                }else if (!requestTargetParsed) {
                    LOGGER.debug("Request line REQ Target to Process: {}", processingDataBuffer.toString());
                    requestTargetParsed = Boolean.TRUE;
                }
                processingDataBuffer.delete(0, processingDataBuffer.length());
            } else {
                processingDataBuffer.append((char) _byte);
                if (!methodParsed) {
                    if(processingDataBuffer.length() > HttpMethod.MAX_LENGTH) {
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }*/

    /**
     * new Method using BuffredReader and split method
     * @param reader
     * @param request
     * @throws IOException
     * @throws HttpParsingException
     */
    private void parseRequestLine(BufferedReader reader, HttpRequest request) throws IOException, HttpParsingException {
        LOGGER.info("Parsing status line");
        
        String requestLine = reader.readLine();
        String[] requestLineElements = requestLine.split(" ");

        if (requestLineElements.length != 3) {
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

        request.setMethod(requestLineElements[0]);
        request.setRequestTarget(requestLineElements[1]);
        request.setHttpVersion(requestLineElements[2]);
    }

    private void parseHeader(BufferedReader ips, HttpRequest request) {
    }

    private void parseBody(BufferedReader ips, HttpRequest request) {
    }

}
