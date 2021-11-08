package com.jhone.auth.services;


import com.jhone.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userrepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userrepository.FindByUserName(username);
        if(user != null){
            return (UserDetails) user;
        }
       else{
           throw  new UsernameNotFoundException("Username"+ username +"Not Found");
        }
    }
}
