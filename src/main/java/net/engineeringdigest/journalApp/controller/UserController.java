package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping
//    public ResponseEntity<List<UserEntity>> getAllUsers(){
//        return userService.getAllUsers();
//    }

    @GetMapping()
    public ResponseEntity<UserEntity> getUserByUserName(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByUserName(userName);
    }

    @GetMapping("journals")
    public ResponseEntity<List<Journal>> getAllJournalsByUsername(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getAllJournalsByUsername(userName);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<Void> editUserById(@PathVariable String id, @RequestBody UserEntity userEntity){
        userEntity.setId(id);
        return userService.editUserById(userEntity);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUserByUserName(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.deleteUserByUserName(userName);
    }
}
