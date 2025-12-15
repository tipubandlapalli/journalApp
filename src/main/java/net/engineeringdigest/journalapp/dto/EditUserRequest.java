package net.engineeringdigest.journalapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import net.engineeringdigest.journalapp.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
public class EditUserRequest {
    private Object var1;

    public UserEntity convert(){
        // to be extended in future
        // this is how we can do it;
        return new UserEntity();
    }
}
