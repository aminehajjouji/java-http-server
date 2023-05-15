package com.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void setUp() {
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateValidGetTestCase());
        } catch (HttpParsingException | IOException e) {
            fail(e);
        }
        assertNotNull(request);
        assertEquals(request.getMethod(), HttpMethod.GET);
        assertEquals(request.getRequestTarget(), "/index.html");
        assertEquals(request.getOriginalHttpVersion(), "HTTP/1.1");
        assertEquals(request.getHttpVersion(), HttpVersion.HTTP_1_1);

    }

    @Test
    void parseHttpRequestBadMethod() throws IOException{
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCaseMethodName());
            fail();
        } catch (HttpParsingException e ) {
            assertEquals(e.getErroCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequestBadMethod2() throws IOException{
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCaseMethodName2());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErroCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequestIvalidNumberOfItems() throws IOException{
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCaseRequestLineIvalidNumberOfItems());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErroCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpEmptyRequestLine() throws IOException{
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCaseEmptyRequestLine());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErroCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpBadHttpVersion() throws IOException {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadHttpVersionTestCase());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErroCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpRequestUnsupportedVersion() throws IOException{
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateUnsupportedHttpVersionTestCase());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErroCode(), HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }
    @Test
    void parseHttpRequestSupportedVersion() throws IOException{
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateSupportedHttpVersion());
            assertNotNull(request);
            assertEquals(request.getHttpVersion(), HttpVersion.HTTP_1_1);
            assertEquals(request.getOriginalHttpVersion(), "HTTP/1.2");
        } catch (HttpParsingException e) {
            fail();
        }
    }

    private InputStream generateValidGetTestCase() {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9," +
                "image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n";
        InputStream ips = new ByteArrayInputStream(request.getBytes(
                StandardCharsets.US_ASCII
        ));
        return ips;
    }

    private InputStream generateBadTestCaseMethodName() {
        String request = "GeT /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n";
        InputStream ips = new ByteArrayInputStream(request.getBytes(
                StandardCharsets.US_ASCII
        ));
        return ips;
    }

    private InputStream generateBadTestCaseMethodName2() {
        String request = "GeTTTTT /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n";
        InputStream ips = new ByteArrayInputStream(request.getBytes(
                StandardCharsets.US_ASCII
        ));
        return ips;
    }

    private InputStream generateBadTestCaseRequestLineIvalidNumberOfItems() {
        String request = "GET /index.html TTTT HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n";
        InputStream ips = new ByteArrayInputStream(request.getBytes(
                StandardCharsets.US_ASCII
        ));
        return ips;
    }
    private InputStream generateBadTestCaseEmptyRequestLine() {
        String request = "\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n";
        InputStream ips = new ByteArrayInputStream(request.getBytes(
                StandardCharsets.US_ASCII
        ));
        return ips;
    }
    private InputStream generateBadHttpVersionTestCase() {
        String request = "GET /index.html HTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "ConnectioTTTTn: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n";
        InputStream ips = new ByteArrayInputStream(request.getBytes(
                StandardCharsets.US_ASCII
        ));
        return ips;
    }
    private InputStream generateUnsupportedHttpVersionTestCase() {
        String request = "GET /index.html HTTP/2.1\r\n" +
                "Host: localhost:8080\r\n" +
                "ConnectioTTTTn: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n";
        InputStream ips = new ByteArrayInputStream(request.getBytes(
                StandardCharsets.US_ASCII
        ));
        return ips;
    }
    private InputStream generateSupportedHttpVersion() {
        String request = "GET /index.html HTTP/1.2\r\n" +
                "Host: localhost:8080\r\n" +
                "ConnectioTTTTn: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n";
        InputStream ips = new ByteArrayInputStream(request.getBytes(
                StandardCharsets.US_ASCII
        ));
        return ips;
    }
}