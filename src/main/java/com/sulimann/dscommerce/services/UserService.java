package com.sulimann.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sulimann.dscommerce.dto.UserDTO;
import com.sulimann.dscommerce.entities.User;
import com.sulimann.dscommerce.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDetails = userRepository.findByEmail(username);
        if(userDetails == null){
            throw new UsernameNotFoundException("Email not found");
        }
        return userDetails;
    }
    
    protected User authenticated(){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(username);

        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid username");
        }
    }

    public UserDTO getMe(){
        User user = authenticated();
        return new UserDTO(user);
    }
    
}
