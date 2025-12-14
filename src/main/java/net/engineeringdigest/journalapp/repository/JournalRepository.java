package net.engineeringdigest.journalapp.repository;

import net.engineeringdigest.journalapp.entity.Journal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends MongoRepository<Journal, String> {
}
