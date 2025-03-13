package com.weather.weatherdashboard.service;

import com.weather.weatherdashboard.model.WeatherResponse;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponse getWeather(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // Safely handle 'main' data
        String temperature = response.containsKey("main")
                ? ((Map<String, Object>) response.get("main")).get("temp").toString() + "°C"
                : "N/A";

        // Safely handle 'weather' data
        String description = "N/A";
        if (response.containsKey("weather")) {
            // Cast 'weather' to ArrayList
            ArrayList<Object> weatherList = (ArrayList<Object>) response.get("weather");
            if (!weatherList.isEmpty()) {
                // Cast the first element of the array to Map
                Map<String, Object> weatherDetails = (Map<String, Object>) weatherList.get(0);
                description = weatherDetails.get("description").toString();
            }
        }

        return new WeatherResponse(city, temperature, description);
    }

}
