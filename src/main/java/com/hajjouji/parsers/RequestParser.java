package com.hajjouji.parsers;

import com.hajjouji.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestParser.class);

    private static final int sp = 0x20;
    private static final int cr = 0x0D;
    private static final int lf = 0x0A;

    public HttpRequest parseHttpRequest(InputStream ips) throws HttpParsingException, IOException {
        // InputStreamReader reader = new InputStreamReader(ips, StandardCharsets.US_ASCII);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ips, StandardCharsets.US_ASCII));
        HttpRequest request = new HttpRequest();
        try {
            parseRequestLine(reader, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseRequestHeader(reader, request);
        parseRequestBody(reader, request);
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
     *
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
        try {
            request.setHttpVersion(requestLineElements[2]);
        } catch (BadHttpVersionException e) {
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    private static void parseRequestHeader(BufferedReader reader, HttpRequest request) throws IOException {
        LOGGER.info("Parsing Headers block");

        Map<String, String> headers = new HashMap<>();
        String singleHeaderLine;
        while ((singleHeaderLine = reader.readLine()) != null && !singleHeaderLine.isBlank()) {
            String[] headerParts = singleHeaderLine.split(": ");
            headers.put(headerParts[0], headerParts[1]);
        }

        request.setHeader(headers);
    }

    private static void parseRequestBody(BufferedReader reader, HttpRequest request) throws IOException {
        LOGGER.info("Parsing Body block");
        if (request.getMethod() != HttpMethod.POST && request.getMethod() != HttpMethod.PUT) {
            return;
        }
        StringBuilder requestBody = new StringBuilder();
        String bodyLineReader;
        while ((bodyLineReader = reader.readLine()) != null && !bodyLineReader.isBlank()) {
            requestBody.append(bodyLineReader);
        }
        request.setBody(requestBody.toString());
    }

}
