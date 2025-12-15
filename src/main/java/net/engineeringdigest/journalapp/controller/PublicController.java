package net.engineeringdigest.journalapp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalapp.api_response.WeatherApi;
import net.engineeringdigest.journalapp.cache.AppCache;
import net.engineeringdigest.journalapp.dto.NewUserCreationRequest;
import net.engineeringdigest.journalapp.entity.UserEntity;
import net.engineeringdigest.journalapp.enums.AppCacheKeys;
import net.engineeringdigest.journalapp.service.EmailService;
import net.engineeringdigest.journalapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("public")
@Slf4j
public class PublicController {

    private final UserService userService;
    private final RestTemplate restTemplate;
    private final AppCache appCache;

    public PublicController(UserService userService, RestTemplate restTemplate, AppCache appCache) {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.appCache = appCache;
    }


    @GetMapping("health-check")
    public String healthCheck(){
        return "HEALTH OK";
    }

    @PostMapping("create-user")
    public ResponseEntity<UserEntity> createUser(@RequestBody NewUserCreationRequest newUserCreationRequest){
        if(newUserCreationRequest.isNotNull()){
            UserEntity userEntity = newUserCreationRequest.convert();
            if(userService.findUserByUserName(userEntity.getUserName()).isPresent()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(userService.createUser(userEntity), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * test purpose only
     */
    @GetMapping("welcome")
    public ResponseEntity<String> welcome(){
        String city = appCache.getAppCacheMap().get(AppCacheKeys.CITY.getValue());
        String string = appCache.getAppCacheMap().get(AppCacheKeys.STRING.getValue());
        String key = appCache.getAppCacheMap().get(AppCacheKeys.KEY.getValue());

        String finalAPI = string.replace("<key>", key).replace("<city>", city);

        ResponseEntity<WeatherApi> response = restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherApi.class);
        String greeting = "Hi there, welcome to journalApp";

        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
            WeatherApi.Current current = response.getBody().getCurrent();
            greeting = greeting + " \n" + "now it's " + current.getTempC() + "Celsius here in " + city + ", but feels like " + current.getFeelslikeC() + " \n" + "it's hint you might to start with";
        } else {
            log.error("Weather API failed with status: {}", response.getStatusCode());
        }
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}
