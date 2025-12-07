package com.example.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.units}")
    private String units;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeatherByCity(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=%s", apiUrl, city, apiKey, units);

        String json = restTemplate.getForObject(url, String.class);
        JSONObject root = new JSONObject(json);

        WeatherResponse response = new WeatherResponse();
        response.setCity(root.getString("name"));

        // weather[0].description
        String description = root.getJSONArray("weather")
                                .getJSONObject(0)
                                .getString("description");
        response.setDescription(description);

        // main.temp, main.feels_like, main.humidity
        JSONObject main = root.getJSONObject("main");
        response.setTemperature(main.getDouble("temp"));
        response.setFeelsLike(main.getDouble("feels_like"));
        response.setHumidity(main.getInt("humidity"));

        return response;
    }
}
