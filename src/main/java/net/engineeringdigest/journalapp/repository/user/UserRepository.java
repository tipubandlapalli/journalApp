package net.engineeringdigest.journalapp.repository.user;

import net.engineeringdigest.journalapp.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUserName(String userName);
    void deleteByUserName(String userName);
}
