# java-http-server

This repository contains a simple Java HTTP server project.
## Dependencies
The project has the following dependencies:

* Lombok - Version 1.18.10
* Jackson Core - Version 2.9.9
* Jackson Databind - Version 2.9.9
* SLF4J - Version 1.7.29
* Logback Classic - Version 1.2.3
* JUnit Jupiter - Version RELEASE (for testing)

## Annotation: RequestMapping
The RequestMapping annotation is a custom annotation used to define the mapping between HTTP requests and the corresponding controller methods. It is used to specify the URL path and HTTP method for each method in the controller.

The RequestMapping annotation has two attributes:

***path: Specifies the URL path pattern for the method.***
<br />
***method: Specifies the HTTP method (GET, POST, etc.) for the method.***
* By using this annotation, you can easily map incoming requests to specific methods in the controller based on the URL and HTTP method.
## Class: Mapper
The Mapper class plays a crucial role in the server by mapping incoming requests to the appropriate controller methods based on the defined routes.
<br />
Here's how the Mapper class works:

1. It scans the specified controller class (in this case, ProductController) for methods annotated with RequestMapping.
2. For each annotated method, it creates a Route object that encapsulates the URL path pattern, HTTP method, and a reference to the method.
3. The Mapper class keeps track of all the created routes in a routes list.
4. When a request is received, the Mapper class matches the request's URL and HTTP method against the defined routes.
5. If a matching route is found, the corresponding controller method is invoked to handle the request and produce a response.

This mapping mechanism allows for clean separation of concerns and enables the server to dynamically handle different routes without hardcoding them.
## Usage
The main class of the project is com.hajjouji.httpserver.HttpServer. This class represents the entry point of the server application. It starts the server and listens for incoming requests.

`package com.hajjouji.httpserver;
import com.hajjouji.httpserver.config.Configuration;
import com.hajjouji.httpserver.config.ConfigurationManager;
import com.hajjouji.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    
    public static void main(String[] args) {
        LOGGER.info("Server Starting...");
        
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
        
        LOGGER.info("Server Started at port: " + configuration.getPort());
        LOGGER.info("Webroot is at: " + configuration.getWebroot());
        
        ServerListenerThread serverListenerThread = null;
        try {
            serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }
`
