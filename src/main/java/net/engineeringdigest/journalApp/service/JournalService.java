package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class JournalService {
    @Autowired
        private JournalRepository journalRepository;

    private static<T extends String> boolean check(T t){
        return t != null && !t.isEmpty();
    }

    public List<Journal> getAllJournals(){
        return journalRepository.findAll();
    }

    public Journal getJournalById(ObjectId id){
        return journalRepository.findById(id).orElse(null);
    }


    public boolean addNewJournal(Journal journal){
        Optional<Journal> existing = journalRepository.findById(journal.getId());
        if(existing.isPresent()){
            return false;
        }
        journalRepository.save(journal);
        return true;
    }

    public boolean deleteJournalById(ObjectId id){
        Optional<Journal> existing = journalRepository.findById(id);
        if(!existing.isPresent()){
            return false;
        }
        journalRepository.deleteById(id);
        return true;
    }

    public boolean editJournalById(ObjectId id, Journal journal){
        Optional<Journal> existing = journalRepository.findById(id);
        if(!existing.isPresent()){
            return false;
        }

        if(check(journal.getTitle())) existing.get().setTitle(journal.getTitle());
        if(check(journal.getContent())) existing.get().setContent(journal.getContent());

        journalRepository.save(existing.get());

        return true;
    }
}
