package net.engineeringdigest.journalApp.service.journal;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class JournalService {
    @Autowired
        private JournalRepository journalRepository;

    @Autowired
        private UserRepository userRepository;


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
