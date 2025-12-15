package net.engineeringdigest.journalapp.dto;

import lombok.*;
import net.engineeringdigest.journalapp.entity.UserEntity;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
public class NewUserCreationRequest {
    private final String userName;
    private final String password;

    public boolean isNotNull(){
        return userName != null && password != null;
    }

    public UserEntity convert(){
        UserEntity userEntity = new UserEntity();
        if(!isNotNull()){
            throw new NullPointerException();
        }
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        return userEntity;
    }
}
