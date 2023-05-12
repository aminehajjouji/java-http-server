package com.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HttpVersionTest {
    @Test
    void getCompatibleVersionExactMatch() {
        HttpVersion version  = null;
        try {
            version = HttpVersion.getCompatibleVersion("HTTP/1.1");
        } catch (BadHttpVersionException e) {
            fail();
        }
        assertNotNull(version);
        assertEquals(version,HttpVersion.HTTP_1_1);

    }
    @Test
    void getCompatibleVersionBasFormat() {
        HttpVersion version  = null;
        try {
            version = HttpVersion.getCompatibleVersion("http/1.1");
            fail();
        } catch (BadHttpVersionException e) {
        }
    }
    @Test
    void getCompatibleVersionHigherVersion() {
        HttpVersion version  = null;
        try {
            version = HttpVersion.getCompatibleVersion("HTTP/1.2");
            assertNotNull(version);
            assertEquals(version,HttpVersion.HTTP_1_1);
        } catch (BadHttpVersionException e) {
            fail();
        }
    }
}

