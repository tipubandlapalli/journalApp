package net.engineeringdigest.journalapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.engineeringdigest.journalapp.entity.UserEntity;

@Getter
@Setter
@Builder
public class EditUserRequest {

    private final Boolean optForSA;
    private final String email;

    public UserEntity convert(){
        UserEntity userEntity = new UserEntity();
        if(optForSA != null) userEntity.setOptForSA(optForSA);
        if(email != null) userEntity.setEmail(email);
        return userEntity;
    }
}
