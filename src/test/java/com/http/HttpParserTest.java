package com.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
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
        httpParser.parseHttpRequest(generateValidTestCase());
    }
    private InputStream generateValidTestCase(){
         String   request = "GET / HTTP/1.1\n" +
                 "Host: localhost:8080\n" +
                 "Connection: keep-alive\n" +
                 "Cache-Control: max-age=0\n" +
                 "sec-ch-ua: \"Chromium\";v=\"112\", \"Google Chrome\";v=\"112\", \"Not:A-Brand\";v=\"99\"\n" +
                 "sec-ch-ua-mobile: ?0\n" +
                 "sec-ch-ua-platform: \"Linux\"\n" +
                 "Upgrade-Insecure-Requests: 1\n" +
                 "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36\n" +
                 "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\n" +
                 "Sec-Fetch-Site: none\n" +
                 "Sec-Fetch-Mode: navigate\n" +
                 "Sec-Fetch-User: ?1\n" +
                 "Sec-Fetch-Dest: document\n" +
                 "Accept-Encoding: gzip, deflate, br\n" +
                 "Accept-Language: fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                 "Cookie: refresh-token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9sb2dpbiIsImV4cCI6MTY4MzU1NzgyOH0.QnLJQBt1eSH0fuFEt9zyjLHgK7dTKiWxi7BwIRDQuP8\n";
         InputStream ips = new ByteArrayInputStream(request.getBytes(
                 StandardCharsets.US_ASCII
         ));
         return ips;
    }
}