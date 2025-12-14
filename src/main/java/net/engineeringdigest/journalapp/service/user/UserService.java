package net.engineeringdigest.journalapp.service.user;

import net.engineeringdigest.journalapp.entity.Journal;
import net.engineeringdigest.journalapp.entity.UserEntity;
import net.engineeringdigest.journalapp.repository.JournalRepository;
import net.engineeringdigest.journalapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JournalRepository journalRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JournalRepository journalRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.journalRepository = journalRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public UserEntity createUser(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userEntity.setRoles(Collections.singletonList("USER"));
        userEntity.setLocalDateTime(LocalDateTime.now());
        userEntity.setJournals(new ArrayList<>());

        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Transactional
    public boolean editUserInfo(UserEntity userEntity, UserEntity existingUserEntity){
        // USER INFO <=> ADDITIONAL INFO (at present nothing)
        // USER DETAILS <=> userName AND password
        // LET IT SAVES THE OLD DATA ONLY (MADE FOR FUTURE USE TO EXTENDED)
        userRepository.save(existingUserEntity);

        return true;
    }

    @Transactional
    public void deleteUserByUserName(String userName){
        Optional<UserEntity> user = userRepository.findByUserName(userName);
        if(user.isPresent()) {
            List<Journal> journals = user.get().getJournals();
            if(!journals.isEmpty()) {
                journalRepository.deleteAll(journals);
            }
            userRepository.deleteByUserName(userName);
        }
    }
}
