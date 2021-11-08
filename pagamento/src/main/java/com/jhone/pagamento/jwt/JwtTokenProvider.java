package com.jhone.pagamento.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    //secret-key: chave_microservices
    //expire-length: 360000
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;






    public Authentication getAuthentication(String token){
        System.out.println("token no getAuthentication.:"+token.toString());
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return "";
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
        };
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token){
        System.out.println("token no valida token.:"+token.toString());
        try {
            //System.out.println("token no valida token 1.:");
            //System.out.println("token no valida token 1 secretKey.:"+secretKey);
            System.out.println("token no valida token 1 token.:");
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.trim());
            if(claims.getBody().getExpiration().before(new Date())){
                return false;
            }

            System.out.println("token no valida token 1 claims.:"+claims);
            //if(claims.getExpiration().before(new Date())){
                //System.out.println("token no valida token 2.:");
              //  return false;
            //}
            //System.out.println("token no valida token 3.:");
            return true;
        }
        catch (JwtException | IllegalArgumentException e){
            System.out.println("token no valida token 4.:"+e.toString());
            return false;
        }
    }

}
