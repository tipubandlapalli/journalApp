package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GetUsernameByToken {
    @Autowired
    private JwtService jwtService;

    public String getUsernameByAuthenticatedToken() throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            String username = authentication.getName();
            if(username == null) throw new UsernameNotFoundException("username not found");
            return authentication.getName();
        }
        throw new RuntimeException("user not authenticated (current time in millis) start " + System.currentTimeMillis() + "end");
    }
}
