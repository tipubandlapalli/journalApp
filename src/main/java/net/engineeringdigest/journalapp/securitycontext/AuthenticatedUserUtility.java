package net.engineeringdigest.journalapp.securitycontext;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalapp.entity.UserEntity;
import net.engineeringdigest.journalapp.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticatedUserUtility {

    private final UserService userService;

    public AuthenticatedUserUtility(UserService userService) {
        this.userService = userService;
    }

//  when we have even one static variable we should not use @AllArgsConstructor because it won't inject that
//  we were using static final Logger now changed to @SLf4j

    public UserEntity getAuthenticatedUserEntity() {
        String userName = getUserName();
        return userService
                .findUserByUserName(userName)
                .orElseThrow(
                        () ->{
                            log.error("CRITICAL SECURITY ANOMALY: Authenticated user [{}] not found in DB", userName);
                            return new UsernameNotFoundException("Authenticated Principle missing from database..");
                        }
                );
    }

    public String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
