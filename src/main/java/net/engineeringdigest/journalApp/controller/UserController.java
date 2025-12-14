package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.securitycontext.AuthenticatedUserUtility;
import net.engineeringdigest.journalApp.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final AuthenticatedUserUtility authenticatedUserUtility;

    public UserController(AuthenticatedUserUtility authenticatedUserUtility, UserService userService) {
        this.authenticatedUserUtility = authenticatedUserUtility;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUser(){
        UserEntity userEntity = authenticatedUserUtility.getAuthenticatedUserEntity();
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> editUserInfo(@RequestBody UserEntity userEntity){
        UserEntity existingUserEntity =  authenticatedUserUtility.getAuthenticatedUserEntity();
        userService.editUserInfo(userEntity, existingUserEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        String userName = authenticatedUserUtility.getUserName();
        userService.deleteUserByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
