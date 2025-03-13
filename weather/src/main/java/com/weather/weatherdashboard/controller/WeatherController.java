package com.weather.weatherdashboard.controller;

import com.weather.weatherdashboard.model.WeatherResponse;
import com.weather.weatherdashboard.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {
        try {
            WeatherResponse weather = weatherService.getWeather(city);
            model.addAttribute("weather", weather); // Add weather data to the model
        } catch (Exception e) {
            model.addAttribute("error", "Could not fetch weather data. Please try again.");
        }
        return "dashboard"; // Return the name of the Thymeleaf template (dashboard.html)
    }
}
