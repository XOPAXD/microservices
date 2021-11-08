package com.jhone.auth.controller;

import com.jhone.auth.jwt.JwtTokenProvider;
import com.jhone.auth.repository.UserRepository;
import com.jhone.auth.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthController {
    @Autowired
    private final AuthenticationManager authenticationmanager;
    @Autowired
    private final JwtTokenProvider jwttokenprovider;
    @Autowired
    private final UserRepository userrepository;

    @RequestMapping("/testeSecurity")
    public String teste(){
        return "testado!!!";
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
                 consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> login(@RequestBody UserVO uservo){
        try {
            System.out.println("resource username.:"+uservo.toString());
            var username = uservo.getUserName();
            var password = uservo.getPassword();




            authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

            var user = userrepository.FindByUserName(username.trim());
            var token = "";

            if(user != null){
                token = jwttokenprovider.createToken(username, user.getRoles());
            } else {
                throw  new UsernameNotFoundException("user name not found");
            }
            Map<Object, Object> model = new HashMap<>();
            model.put("username",username);
            model.put("token",token);

            return ok(model);

        }catch (AuthenticationException e){
            throw  new BadCredentialsException("Ivalid username/password");
        }
    }
}
