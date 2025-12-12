package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.Journal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends MongoRepository<Journal, String> {
}
