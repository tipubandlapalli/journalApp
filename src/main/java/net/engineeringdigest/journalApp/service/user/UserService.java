package net.engineeringdigest.journalApp.service.user;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    @Autowired
            private UserRepository userRepository;

    @Autowired
            private JournalRepository journalRepository;

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
