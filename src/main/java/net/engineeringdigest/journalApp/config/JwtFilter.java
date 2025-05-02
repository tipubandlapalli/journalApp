package net.engineeringdigest.journalApp.config;

import net.engineeringdigest.journalApp.service.JwtService;
import net.engineeringdigest.journalApp.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    public ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        System.out.println(authHeader);
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                System.out.println(token);
                username = jwtService.extractUserName(token);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("fuck off here");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println(username);
                UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
