package com.httpserver;

import com.httpserver.config.Configuration;
import com.httpserver.config.ConfigurationManager;

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("Server Starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().gerCurrentConfiguration();
        System.out.println("Server Started at port: " + configuration.getPort());
        System.out.println("Webroot is at: " + configuration.getWebroot());
    }
}
