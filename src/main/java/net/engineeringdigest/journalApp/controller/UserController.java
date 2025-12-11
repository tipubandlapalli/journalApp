package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.UserService;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("user/{userName}")
    public ResponseEntity<UserEntity> getUserByUserName(@PathVariable String userName){
        return userService.findUserByUserName(userName);
    }

    @GetMapping("user/{userName}/journal")
    public ResponseEntity<List<Journal>> getAllJournals(@PathVariable String userName){
        return userService.getAllJournals(userName);
    }
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){
        return userService.createUser(userEntity);
    }

    @PutMapping("user/{id}")
    public ResponseEntity<Void> editUserById(@PathVariable ObjectId id, @RequestBody UserEntity userEntity){
        userEntity.setId(id);
        return userService.editUserById(userEntity);
    }

    @DeleteMapping("user/{userName}")
    public ResponseEntity<Void> deleteUserByUserName(@PathVariable String userName){
        return userService.deleteUserByUserName(userName);
    }
}
