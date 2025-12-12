package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("{userName}")
    public ResponseEntity<UserEntity> getUserByUserName(@PathVariable String userName){
        return userService.findUserByUserName(userName);
    }

    @GetMapping("{userName}/journal")
    public ResponseEntity<List<Journal>> getAllJournalsByUsername(@PathVariable String userName){
        return userService.getAllJournalsByUsername(userName);
    }
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){
        return userService.createUser(userEntity);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<Void> editUserById(@PathVariable String id, @RequestBody UserEntity userEntity){
        userEntity.setId(id);
        return userService.editUserById(userEntity);
    }

    @DeleteMapping("{userName}")
    public ResponseEntity<Void> deleteUserByUserName(@PathVariable String userName){
        return userService.deleteUserByUserName(userName);
    }
}
