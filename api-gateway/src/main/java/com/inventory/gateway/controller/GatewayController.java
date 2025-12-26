package com.inventory.gateway.controller;

import com.inventory.gateway.ApiGatewayApplication;
import com.inventory.gateway.config.RestTemplateConfig;
import com.inventory.gateway.registry.ServiceRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class GatewayController {

    private final RestTemplateConfig restTemplateConfig;

    private final ApiGatewayApplication apiGatewayApplication;

    private final RestTemplate restTemplate;

    public GatewayController(RestTemplate restTemplate, ApiGatewayApplication apiGatewayApplication, RestTemplateConfig restTemplateConfig) {
        this.restTemplate = restTemplate;
        this.apiGatewayApplication = apiGatewayApplication;
        this.restTemplateConfig = restTemplateConfig;
    }

    @RequestMapping("/auth/**")
    public ResponseEntity<?> forwardAuth(HttpServletRequest request,
                                         @RequestBody(required = false) String body) {
    		System.out.println(request+body);
    		System.out.println("gateway auth hitted");
        return forward(request, body, ServiceRegistry.AUTH_SERVICE, true);
    }

    @RequestMapping("/orders/**")
    public ResponseEntity<?> forwardOrders(HttpServletRequest request,
                                           @RequestBody(required = false) String body) {

        return forward(request, body, ServiceRegistry.ORDER_SERVICE, false);
    }

    @RequestMapping("/inventory/**")
    public ResponseEntity<?> forwardInventory(HttpServletRequest request,
                                              @RequestBody(required = false) String body) {

        return forward(request, body, ServiceRegistry.INVENTORY_SERVICE, false);
    }

    @RequestMapping("/payments/**")
    public ResponseEntity<?> forwardPayments(HttpServletRequest request,
                                             @RequestBody(required = false) String body) {

        return forward(request, body, ServiceRegistry.PAYMENT_SERVICE, false);
    }

    private ResponseEntity<?> forward(HttpServletRequest request,
                                      String body,
                                      String baseUrl,
                                      boolean isAuthCall) {

        String query = request.getQueryString();
        String fullUrl = baseUrl + request.getRequestURI()
                + (query != null ? "?" + query : "");

        HttpHeaders headers = new HttpHeaders();

        request.getHeaderNames().asIterator()
                .forEachRemaining(h -> {
                    if (!h.equalsIgnoreCase("content-length")
                            && !(isAuthCall && h.equalsIgnoreCase("authorization"))) {
                        headers.add(h, request.getHeader(h));
                    }
                });

        if (!isAuthCall && request.getAttribute("X-User") != null) {
            headers.add("X-User", request.getAttribute("X-User").toString());
            headers.add("X-Role", request.getAttribute("X-Role").toString());
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                fullUrl,
                HttpMethod.valueOf(request.getMethod()),
                entity,
                String.class
        );
    }
}
