package com.example.mvc;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // GET /api/weather?city=Pune
    @GetMapping
    public WeatherResponse getWeather(@RequestParam String city) {
        return weatherService.getWeatherByCity(city);
    }
}
