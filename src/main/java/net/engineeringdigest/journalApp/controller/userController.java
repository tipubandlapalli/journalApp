package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private UserService service;
    @Autowired
    private GetUsernameByToken getUsernameByToken;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        return service.verify(user);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        return service.createUser(user);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getByUsername(){
        return service.getByUsername(getUsernameByToken.getUsernameByAuthenticatedToken());
    }

    @PutMapping
    public ResponseEntity<?> editUser(@RequestBody User user){
        return service.editUser(getUsernameByToken.getUsernameByAuthenticatedToken(),user);
    }
    @DeleteMapping
    public ResponseEntity<?> delUser(){
        return service.delUser(getUsernameByToken.getUsernameByAuthenticatedToken());
    }

}
