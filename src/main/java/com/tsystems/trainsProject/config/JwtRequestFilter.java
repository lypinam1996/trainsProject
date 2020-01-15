package com.tsystems.trainsProject.config;

import com.tsystems.trainsProject.services.impl.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtRequestFilter
{
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil          jwtTokenUtil;

    public void doFilterInternal(String jwtToken)
    {
        String username = "";
        try
        {
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Unable to get JWT Token");
        }
        catch (ExpiredJwtException e)
        {
            System.out.println("JWT Token has expired");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
        {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }
}
