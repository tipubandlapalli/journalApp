package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    Optional<UserEntity> findByUserName(String userName);
    boolean deleteByUserName(String userName);
}
