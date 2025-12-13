package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.enums.Profiles;
import net.engineeringdigest.journalApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
@Profile(Profiles.DEV_PROFILE) // <-- FIX HERE
public class AdminController {
    @Autowired
            private UserService userService;
    @GetMapping("all-users")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return userService.getAllUsers();
    }
}
