package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api_response.WeatherApi;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
            private UserService userService;
    @Autowired
            private  RestTemplate restTemplate;

    @Value("${weather.api.key}") private String API_KEY;
    @Value("${weather.api.string}") private String API_STRING;
    @GetMapping("health-check")
    public String healthCheck(){
        return "HEALTH OK";
    }

    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity){
        if(userEntity.getId() == null && userEntity.getPassword() != null && userEntity.getUserName() != null ){
            if(!userService.findUserByUserName(userEntity.getUserName()).isPresent()){
                return new ResponseEntity<>("try with different user name", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(userService.createUser(userEntity), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("in correct format", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("welcome")
    public ResponseEntity<String> welcome(){
        String city = "London";
        String finalAPI = String.format(
                "http://%s?key=%s&q=%s&api=no",
                API_STRING,
                API_KEY,
                city
        );
        ResponseEntity<WeatherApi> response = restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherApi.class);
        String greeting = "Hi there, welcome to journalApp ";

        if(response.getStatusCode().is2xxSuccessful()){
            WeatherApi.Current current = response.getBody().getCurrent();
            greeting = greeting +
                    " \n" + "now it is " + current.getTempC() + "Celsius here in " + city + ", but feels like " + current.getFeelslikeC() +
                    " \n" + "it's hint you might to start with";
        } else {
            log.error("check weather api response");
            throw new RuntimeException("weather api gone wrong");
        }
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}
