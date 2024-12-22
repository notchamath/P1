package com.revature.services;

import com.revature.models.DTOs.LoginDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.repositories.AuthDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthDAO authDAO;

    @Autowired
    public AuthService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public OutgoingUserDTO login(LoginDTO loginDTO){
        User user = authDAO.findByUserNameAndPassword(
                loginDTO.getUsername(), loginDTO.getPassword()
        );

        if (user == null) {
            throw new IllegalArgumentException("No user found with those credentials");
        }

        return new OutgoingUserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}