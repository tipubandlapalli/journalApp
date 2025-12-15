package net.engineeringdigest.journalapp.repository;

import net.engineeringdigest.journalapp.entity.AppCacheEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppCacheRepository extends MongoRepository<AppCacheEntity, String> {
}
