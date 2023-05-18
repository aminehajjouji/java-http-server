package com.hajjouji.controller;

import com.hajjouji.annotation.RequestMapping;
import com.hajjouji.http.HttpMethod;
import com.hajjouji.http.HttpRequest;
import com.hajjouji.http.HttpStatusCode;
import com.hajjouji.http.ResponseEntity;
import com.hajjouji.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private static final Pattern productIdRegex = Pattern.compile("/products/(?<productId>\\d+)");

    private static final List<Product> products = getLDefaultProducts();
    @RequestMapping(path = "/products", method = HttpMethod.GET)
    public static ResponseEntity<List<Product>> getProducts(HttpRequest request) {
        log.info("Request to get all products");
        ResponseEntity<List<Product>> responseEntity = ResponseEntity.builder(HttpStatusCode.OK, products);
        responseEntity.setStatusCode(HttpStatusCode.OK);
        responseEntity.addHeader("Some-Header", "every thing goes ok :)");
        return responseEntity;
    }
    @RequestMapping(path = "/products/{productId}", method = HttpMethod.GET)
    public static ResponseEntity<Product> getProductsById(HttpRequest request) {
        Matcher matcher = productIdRegex.matcher(request.getRequestTarget());
        Product product = null;
        if (matcher.find()) {
            int productId = Integer.parseInt(matcher.group("productId"));
            product = products.stream().filter(u -> u.getId() == productId).findAny().orElse(new Product());
        }
        return ResponseEntity.builder(HttpStatusCode.OK, product);
    }
    private static List<Product> getLDefaultProducts() {
        List<Product> products = List.of(
                new Product(1,"Running Shoes", "High-quality running shoes for maximum performance", 1.0),
                new Product(2,"Yoga Mat", "Non-slip yoga mat for comfortable workouts", 2.0),
                new Product(3,"Fitness Tracker", "Smart fitness tracker to monitor your daily activity", 3.0),
                new Product(4,"Sports Water Bottle", "Durable water bottle for staying hydrated during workouts", 4.0),
                new Product(5,"Resistance Bands", "Versatile resistance bands for strength training", 5.0)
        );
        return products;

    }


}
