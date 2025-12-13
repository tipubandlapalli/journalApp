package net.engineeringdigest.journalApp.securitycontext;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserUtility {

    @Autowired
    private UserService userService;

    private  static final Logger logger = LoggerFactory.getLogger(AuthenticatedUserUtility.class);

    public UserEntity getAuthenticatedUserEntity() throws RuntimeException {
        String userName = getUserName();
        return userService
                .findUserByUserName(userName)
                .orElseThrow(
                        () ->{
                            logger.error("CRITICAL SECURITY ANOMALY: Authenticated user [{}] not found in DB", userName);
                            return new UsernameNotFoundException("Authenticated Principle missing from database..");
                        }
                );
    }

    public String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
