package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
            private UserService userService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "HEALTH OK";
    }

    @PostMapping("create-user")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){
        return userService.createUser(userEntity);
    }
}
