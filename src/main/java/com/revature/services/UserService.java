package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User registerUser(User user){

        User foundUser = userDAO.findByUserName(user.getUserName());

        if(foundUser != null){
            throw new IllegalArgumentException("Username already exists");
        }

        if(user.getFirstName() == null || user.getFirstName().isBlank()){
            throw new IllegalArgumentException("User First Name cannot be blank or null");
        }

        if(user.getLastName() == null || user.getLastName().isBlank()){
            throw new IllegalArgumentException("User Last Name name cannot be blank or null");
        }

        if(user.getUserName() == null || user.getUserName().isBlank()){
            throw new IllegalArgumentException("User UserName cannot be blank or null");
        }

        if(user.getPassword() == null || user.getPassword().isBlank()){
            throw new IllegalArgumentException("User Password cannot be blank or null");
        }

        return userDAO.save(user);
    }
}
