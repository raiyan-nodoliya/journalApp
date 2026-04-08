package com.ngineeringdigest.journalApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ngineeringdigest.journalApp.Cache.AppCache;
import com.ngineeringdigest.journalApp.api.response.WeatherResponse;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {

        String redisKey = "weather_of_" + city;

        WeatherResponse weatherResponse = redisService.get(redisKey, WeatherResponse.class);

        if (weatherResponse != null) {
            return weatherResponse;
        }

        String apiTemplate = appCache.APP_CACHE.get(AppCache.keys.weather_api.toString());

        String finalAPI = apiTemplate
                .replace("<city>", city)
                .replace("<apikey>", apiKey);

        ResponseEntity<WeatherResponse> response =
                restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

        WeatherResponse body = response.getBody();

        if (body != null) {
            redisService.set(redisKey, body, 300L); // cache for 5 minutes
        }

        return body;
    }
}