package com.jhone.pagamento.jwt;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    @Autowired
    private final JwtTokenProvider tokenprovider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = tokenprovider.resolveToken((HttpServletRequest) servletRequest);
        System.out.println("token no fileter 1.:"+token.toString());
        if(token != null ){ //tokenprovider.validateToken(token)
            System.out.println("token no fileter 2.:"+token.toString());
            Authentication auth = tokenprovider.getAuthentication(token);

            if(auth != null){
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
