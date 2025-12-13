package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity){
        if(userEntity.getId() == null && userEntity.getPassword() != null && userEntity.getUserName() != null ){
            if(!userService.findUserByUserName(userEntity.getUserName()).isPresent()){
                return new ResponseEntity<>("try with different user name", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(userService.createUser(userEntity), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("in correct format", HttpStatus.BAD_REQUEST);
    }
}
