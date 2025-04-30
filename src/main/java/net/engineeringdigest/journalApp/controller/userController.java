package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private UserService service;
    @GetMapping("/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username){
        return service.getByUsername(username);
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        return service.createUser(user);
    }
    @PutMapping("{userId}")
    public ResponseEntity<?> editUser(@PathVariable ObjectId userId, @RequestBody User user){
        return service.editUser(userId,user);
    }
    @DeleteMapping("{username}")
    public ResponseEntity<?> delUser(@PathVariable String username){
        return service.delUser(username);
    }


}
