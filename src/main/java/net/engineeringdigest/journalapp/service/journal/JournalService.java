package net.engineeringdigest.journalapp.service.journal;

import net.engineeringdigest.journalapp.entity.Journal;
import net.engineeringdigest.journalapp.entity.UserEntity;
import net.engineeringdigest.journalapp.repository.JournalRepository;
import net.engineeringdigest.journalapp.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class JournalService {
    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    public JournalService(JournalRepository journalRepository, UserRepository userRepository) {
        this.journalRepository = journalRepository;
        this.userRepository = userRepository;
    }

    public Journal getJournalOfUserById(String id, UserEntity userEntity) {
        return userEntity
                .getJournals()
                .stream()
                .filter(j -> j.getId().equals(id))
                .findAny()
                .orElse(
                        null
                );
    }

    @Transactional
    public Journal addNewJournal(Journal journal, UserEntity userEntity){
        journal.setLocalDateTime(LocalDateTime.now());
            Journal saved = journalRepository.save(journal);
        userEntity.getJournals().add(saved);
            userRepository.save(userEntity);
        return saved;
    }

    @Transactional
    public boolean deleteJournalById(String id, UserEntity userEntity){
        boolean isDeleted = userEntity.getJournals().removeIf(j -> j.getId().equals(id));
        if(isDeleted) {
            journalRepository.deleteById(id);
            userRepository.save(userEntity);
            return true;
        } else {
            return  false;
        }
    }

    @Transactional
    public boolean editJournalById(String id, Journal journal, UserEntity userEntity){

        Optional<Journal> existingJournal = userEntity.getJournals().stream().filter(j -> j.getId().equals(id)).findAny();

        if(existingJournal.isPresent()) {
            if(journal.getTitle() != null) existingJournal.get().setTitle(journal.getTitle());
            if(journal.getContent() != null) existingJournal.get().setContent(journal.getContent());
            journalRepository.save(existingJournal.get());
            return true;
        }
        return false;

    }
}
