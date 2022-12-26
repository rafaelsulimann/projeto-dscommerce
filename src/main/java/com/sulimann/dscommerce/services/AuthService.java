package com.sulimann.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sulimann.dscommerce.entities.User;
import com.sulimann.dscommerce.services.exceptions.ForbiddenException;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validatedSelfOrAdmin(Long userId){
        User user = userService.authenticated();
        if(!user.hasRole("ROLE_ADMIN") && !user.getId().equals(userId)){
            throw new ForbiddenException("Acesso negado");
        }
    }
    
}
