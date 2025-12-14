package net.engineeringdigest.journalapp.entity;

import java.time.LocalDateTime;
import java.util.List;


import lombok.Data;
import lombok.NonNull;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    @NonNull // NonNull -> lombok
    @Indexed(unique = true)
    private String userName;

    @NonNull
    @ToString.Exclude // secure from logging
    private String password;

    @DBRef
    private List<Journal> journals;

    private List<String> roles;

    private LocalDateTime localDateTime;
}
