package net.engineeringdigest.journalapp.service.user;

import net.engineeringdigest.journalapp.entity.UserEntity;
import net.engineeringdigest.journalapp.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUserName(username);
        if(user.isPresent()){
            return User.builder()
                    .username(username)
                    .password(user.get().getPassword())
                    .roles(user.get().getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("user not found");
    }
}
