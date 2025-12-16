package net.engineeringdigest.journalapp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalapp.dto.NewUserCreationRequest;
import net.engineeringdigest.journalapp.entity.UserEntity;
import net.engineeringdigest.journalapp.service.WeatherService;
import net.engineeringdigest.journalapp.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
@Slf4j
public class PublicController {

    private final UserService userService;
    private final WeatherService weatherService;

    public PublicController(UserService userService, WeatherService weatherService) {
        this.userService = userService;
        this.weatherService = weatherService;
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
    @GetMapping("welcome/{city}")
    public ResponseEntity<String> welcome(@PathVariable String city){
        return new ResponseEntity<>(weatherService.getGreeting(city), HttpStatus.OK);
    }
}
