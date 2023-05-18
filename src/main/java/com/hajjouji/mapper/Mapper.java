package com.hajjouji.mapper;

import com.hajjouji.annotation.RequestMapping;
import com.hajjouji.controller.ProductController;
import com.hajjouji.http.HttpMethod;
import com.hajjouji.http.ResponseEntity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Mapper {
    public final static List<Route> routes = new ArrayList<>();

    public static void addRoute(Route route) {
        routes.add(route);
    }

    static {
        scanController(ProductController.class);
    }

    private static void scanController(Class<ProductController> productControllerClass) {
        Method[] methods = productControllerClass.getDeclaredMethods();
        Object controllerInstance;
        try {
            controllerInstance = productControllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        for (Method method : methods) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            if (annotation != null) {
                Pattern pathPattern = createPathPattern(annotation.path());
                HttpMethod httpMethod = annotation.method();

                // Use reflection to create a method reference
                RequestHandler handler = createMethodReference(controllerInstance, method);

                addRoute(new Route(pathPattern, httpMethod, handler));
            }
        }
    }

    private static RequestHandler createMethodReference(Object controllerInstance, Method method) {
        return (request) -> {
            try {
                return (ResponseEntity<?>) method.invoke(controllerInstance, request);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };

    }
    private static Pattern createPathPattern(String path) {
        // Check if the path contains parameters
        if (path.contains("{")) {
            // Replace curly braces with named capturing groups for variables
            String regex = path.replaceAll("\\{([^/]*)}", "(?<$1>[^/]+)");
                return Pattern.compile(regex);
        } else {
            // Escape special characters in the path and create an exact match pattern
            String escapedPath = Pattern.quote(path);
            return Pattern.compile("^" + escapedPath + "$");
        }
    }
}