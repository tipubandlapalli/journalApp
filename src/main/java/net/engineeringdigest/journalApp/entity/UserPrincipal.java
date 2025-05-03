package net.engineeringdigest.journalApp.entity;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class UserPrincipal implements UserDetails {
    private User user;
    public UserPrincipal(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    //    return Collections.singleton(user.getRoles().forEach(role ->   new SimpleGrantedAuthority(role) ));
        // roles should be with prefix ROLE_
        return user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        // role -> new SimpleGrantedAuthority(role)
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
