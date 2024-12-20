package com.revature.services;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final ReimbursementDAO reimbursementDAO;

    public UserService(UserDAO userDAO, ReimbursementDAO reimbursementDAO) {
        this.userDAO = userDAO;
        this.reimbursementDAO = reimbursementDAO;
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


    public List<Reimbursement> getUserReimbursements(int userId, String status){

        if (userId < 0) throw new IllegalArgumentException("User ID is not valid");

        Optional<User> foundUser = userDAO.findById(userId);

        if (foundUser.isEmpty()) {
            throw new IllegalArgumentException("No user found by that ID");
        } else {
            if(status == null || status.isEmpty()) {
                return reimbursementDAO.findByUser_UserId(userId);
            } else {
                if(!status.equalsIgnoreCase("pending")
                        && !status.equalsIgnoreCase("approved")
                        && !status.equalsIgnoreCase("denied")
                ){
                    throw new IllegalArgumentException("Query string can be either pending, approved or denied");
                } else {
                    List<Reimbursement> allReimbs = reimbursementDAO.findByUser_UserId(userId);
                    return allReimbs.stream().filter(reimb -> reimb.getStatus().equals(status))
                            .collect(Collectors.toList());
                }
            }
        }
    }
}
