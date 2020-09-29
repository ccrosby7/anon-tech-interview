package com.crosby.anontech.util;

import com.crosby.anontech.config.ApplicationProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class MetricRequestUtil {

    private static final String VALUE = "value";

    private static final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(MetricRequestUtil.class);

    private MetricRequestUtil() { throw new IllegalStateException("Utility class"); }

    private static PassiveExpiringMap<String, Double> diameterMap = new PassiveExpiringMap<>(60, TimeUnit.SECONDS); //change to configurable

    public static double requestCableDiameter(String url) throws ExecutionException, InterruptedException {
        var response = httpRequest(url).get(); //block for response
        Double cableDiameter = Double.MIN_VALUE;
        try {
            cableDiameter = jsonMapper.readTree(response.body()).get(VALUE).asDouble(); //make const
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        diameterMap.put(LocalDateTime.now().toString(), cableDiameter);
        var movingAverage = getMovingAverage();
        log.info("Current moving average is: {}", movingAverage);
        return movingAverage;
    }

    public static void requestCableDiameterAsync(String url) {
        httpRequest(url).thenApply(response -> {
            Double cableDiameter = Double.MIN_VALUE;
            try {
             cableDiameter = jsonMapper.readTree(response.body()).get(VALUE).asDouble(); //make const
            } catch (JsonProcessingException e) {
             log.error(e.getMessage());
            }
            diameterMap.put(LocalDateTime.now().toString(), cableDiameter);
            var movingAverage = getMovingAverage();
            log.info("Current moving average is: {}", movingAverage);
            return movingAverage;
        });
    }

    public static double getMovingAverage(){
        var averageDiameter = diameterMap.values().stream().mapToDouble(value -> value).average(); //may take a hot min as only when .values is called are entries invalidated
        if(averageDiameter.isPresent()){
            return averageDiameter.getAsDouble();
        }
        return -1; //negative diameter is impossible
    }

    private static CompletableFuture<HttpResponse<String>> httpRequest(String url){

        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .timeout(Duration.ofSeconds(10)) //default time out in case endpoint dies
            .uri(URI.create(url))
            .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());


    }

}
