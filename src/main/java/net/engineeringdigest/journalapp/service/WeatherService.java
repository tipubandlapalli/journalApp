package net.engineeringdigest.journalapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.binding.When;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalapp.api_response.WeatherApi;
import net.engineeringdigest.journalapp.cache.AppCache;
import net.engineeringdigest.journalapp.enums.AppCacheKeys;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherService {

    private final RestTemplate restTemplate;
    private final AppCache appCache;
    private final RedisService redisService;

    private WeatherApi getWeatherFromApp(String city){
        String string = appCache.getAppCacheMap().get(AppCacheKeys.STRING.getValue());
        String key = appCache.getAppCacheMap().get(AppCacheKeys.KEY.getValue());

        String finalAPI = string.replace("<key>", key).replace("<city>", city);
        ResponseEntity<WeatherApi> response = restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherApi.class);

        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
            return response.getBody();
        } else {
            log.error("Weather API failed with status: {}", response.getStatusCode());
            return null;
        }
    }

    private WeatherApi getWeather(String city){
        String key = AppCacheKeys.WEATHER_OF_ + city.toUpperCase();
        try {
            WeatherApi weatherApi = redisService.get(key, WeatherApi.class);
            if(weatherApi != null) {
            } else {
                weatherApi = getWeatherFromApp(city);
                if(weatherApi != null){
                    redisService.set(key, weatherApi);
                }
            }
            return weatherApi;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getGreeting(String city){
        String greeting = "Hi there, welcome to journalApp";
        WeatherApi response = getWeather(city);
        System.out.println(response.toString());
        if(response != null) {
            WeatherApi.Current current = response.getCurrent();
            greeting = greeting + " \n" + "now it's " + current.getTempC() + "Celsius here in " + city + ", but feels like " + current.getFeelslikeC() + " \n" + "it's hint you might to start with";
        }
        return greeting;
    }
}
