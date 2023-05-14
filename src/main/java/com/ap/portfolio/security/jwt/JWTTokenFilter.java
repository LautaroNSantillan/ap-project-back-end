package com.ap.portfolio.security.jwt;

import com.ap.portfolio.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JWTTokenFilter extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(JWTProvider.class);
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = this.getToken(request);
            if(token!=null && this.jwtProvider.validateToken(token)){
                String username = this.jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = this.userDetails.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch(Exception e){
            logger.error("DoFilterInternal Failed");
        }
        filterChain.doFilter(request, response);
    }
    private String getToken(HttpServletRequest req){
        String header = req.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer")){
            return header.replace("Bearer", "");
        }
        return null;
    }
}
