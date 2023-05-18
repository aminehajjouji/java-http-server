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
***method: Specifies the HTTP method (GET, POST, etc.) for the method.***
