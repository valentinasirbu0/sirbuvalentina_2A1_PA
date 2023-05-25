package net.javaguides.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class APITestApplication {

    public static void main(String[] args) {
        int concurrentRequests = 10; // Number of concurrent requests to send
        int totalRequests = 100; // Total number of requests to send
        String apiUrl = "http://localhost:8080/api/v1/players"; // Replace with your API URL

        AtomicInteger successfulRequests = new AtomicInteger(0);
        AtomicInteger failedRequests = new AtomicInteger(0);

        RestTemplate restTemplate = new RestTemplate();
        ExecutorService executorService = Executors.newFixedThreadPool(concurrentRequests);

        Instant start = Instant.now();

        for (int i = 0; i < totalRequests; i++) {
            executorService.submit(() -> {
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        successfulRequests.incrementAndGet();
                    } else {
                        failedRequests.incrementAndGet();
                    }
                } catch (Exception e) {
                    failedRequests.incrementAndGet();
                }
            });
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
            // Wait for all requests to complete
        }

        Instant end = Instant.now();
        long elapsedTimeMillis = Duration.between(start, end).toMillis();

        int successfulCount = successfulRequests.get();
        int failedCount = failedRequests.get();
        int totalRequestsSent = successfulCount + failedCount;

        System.out.println("Total requests sent: " + totalRequestsSent);
        System.out.println("Successful requests: " + successfulCount);
        System.out.println("Failed requests: " + failedCount);
        System.out.println("Elapsed time: " + elapsedTimeMillis + " ms");
        System.out.println("Requests per minute: " + (totalRequestsSent / (elapsedTimeMillis / 1000.0)) * 60);
    }
}

