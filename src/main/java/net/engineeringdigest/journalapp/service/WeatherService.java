package net.engineeringdigest.journalapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalapp.api_response.WeatherApi;
import net.engineeringdigest.journalapp.cache.AppCache;
import net.engineeringdigest.journalapp.enums.AppCacheKeys;
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

    private ResponseEntity<WeatherApi> getWeatherFromApp(String city){
        String string = appCache.getAppCacheMap().get(AppCacheKeys.STRING.getValue());
        String key = appCache.getAppCacheMap().get(AppCacheKeys.KEY.getValue());

        String finalAPI = string.replace("<key>", key).replace("<city>", city);
        return restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherApi.class);
    }

    private WeatherApi getWeather(String city){
        String key = AppCacheKeys.WEATHER_OF_ + city.toUpperCase();
        WeatherApi weatherApi = null;
        try {
            weatherApi = redisService.get(key, WeatherApi.class);
            if(weatherApi == null) {
                ResponseEntity<WeatherApi> response = getWeatherFromApp(city);
                if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null ){
                    weatherApi = response.getBody();
                    redisService.set(key, weatherApi);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            return weatherApi;
        }
    }

    public String getGreeting(String city){
        String greeting = "Hi there, welcome to journalApp";
        WeatherApi response = getWeather(city);

        if(response != null) {
            WeatherApi.Current current = response.getCurrent();
            greeting = greeting + " \n" + "now it's " + current.getTempC() + "Celsius here in " + city + ", but feels like " + current.getFeelslikeC() + " \n" + "it's hint you might to start with";
        }
        return greeting;
    }
}
