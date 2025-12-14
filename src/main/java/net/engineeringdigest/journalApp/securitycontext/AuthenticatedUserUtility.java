package net.engineeringdigest.journalApp.securitycontext;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.user.UserService;
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

    //    private  static final Logger logger = LoggerFactory.getLogger(AuthenticatedUserUtility.class);
//    when we have even one static variable we should not use @AllArgsConstructor because it won't inject that

    public UserEntity getAuthenticatedUserEntity() throws RuntimeException {
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
