package com.hajjouji.httpserver;

import com.hajjouji.httpserver.config.Configuration;
import com.hajjouji.httpserver.config.ConfigurationManager;
import com.hajjouji.httpserver.core.ServerListnerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    public static void main(String[] args) {
        LOGGER.info("Server Starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().gerCurrentConfiguration();
        LOGGER.info("Server Started at port: " + configuration.getPort());
        LOGGER.info("Webroot is at: " + configuration.getWebroot());
        ServerListnerThread serverListnerThread = null;
        try {
            serverListnerThread = new ServerListnerThread(configuration.getPort(), configuration.getWebroot());
            serverListnerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
