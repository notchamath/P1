package com.revature.services;

import com.revature.models.DTOs.OutgoingReimbDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
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

    public OutgoingUserDTO registerUser(User user){

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

        User newUser = userDAO.save(user);

        return new OutgoingUserDTO(newUser.getUserId(), newUser.getUserName(), newUser.getRole(), newUser.getFirstName(), newUser.getLastName());
    }


    public List<OutgoingUserDTO> getAllUsers(){
        List<User> users = userDAO.findAll(Sort.by("userId").ascending());

        List<OutgoingUserDTO> outgoingUsers = new ArrayList<>();

        for(User user: users){
            outgoingUsers.add(new OutgoingUserDTO(
                    user.getUserId(),
                    user.getUserName(),
                    user.getRole(),
                    user.getFirstName(),
                    user.getLastName()
            ));
        }

        return outgoingUsers;
    }


    public List<OutgoingReimbDTO> getUserReimbursements(int userId, String status, int loggedInUserId){

        if (userId < 0) throw new IllegalArgumentException("User ID is not valid");
        if (userId != loggedInUserId) throw new IllegalArgumentException("Logged in user requested Reimbursements of a different user");

        Optional<User> foundUser = userDAO.findById(userId);

        if (foundUser.isEmpty()) {
            throw new IllegalArgumentException("No user found by that ID");
        }

        List<Reimbursement> reimbursements = reimbursementDAO.findByUser_UserId(userId);
        List<OutgoingReimbDTO> outgoingReimbs = new ArrayList<>();
        for(Reimbursement reimb: reimbursements){
            outgoingReimbs.add(
                    new OutgoingReimbDTO(
                            reimb.getReimbId(),
                            reimb.getDescription(),
                            reimb.getAmount(),
                            reimb.getStatus(),
                            reimb.getUser().getUserId()
                    )
            );
        }

        if(status == null || status.isEmpty()) {
            return outgoingReimbs;
        } else {
            return getUserReimbursementsByStatus(outgoingReimbs, status);
        }

    }

    public List<OutgoingReimbDTO> getUserReimbursementsByStatus(List<OutgoingReimbDTO> outgoingReimbs, String status){
        if(!status.equalsIgnoreCase("pending")
                && !status.equalsIgnoreCase("approved")
                && !status.equalsIgnoreCase("denied")
        ){
            throw new IllegalArgumentException("Query string can be either pending, approved or denied");
        } else {
            return outgoingReimbs.stream().filter(reimb -> reimb.getStatus().equals(status))
                    .collect(Collectors.toList());
        }
    }


    public OutgoingUserDTO deleteUser(int userId){
        Optional<User> user = userDAO.findById(userId);
        if(!user.isEmpty()){
            userDAO.deleteById(userId);
            return new OutgoingUserDTO(
                    user.get().getUserId(),
                    user.get().getUserName(),
                    user.get().getRole(),
                    user.get().getFirstName(),
                    user.get().getLastName()
            );
        }

       throw new IllegalArgumentException("Failed to delete record from the database, check Id and try again");
    }


    public OutgoingUserDTO updateUserRole(int userId, String role){
        Optional<User> user = userDAO.findById(userId);

        if(!user.isEmpty() && role != null && (role.equalsIgnoreCase("manager") || role.equalsIgnoreCase("employee"))){
            user.get().setRole(role);
            User updatedUser = userDAO.save(user.get());

            return new OutgoingUserDTO(
                    updatedUser.getUserId(),
                    updatedUser.getUserName(),
                    updatedUser.getRole(),
                    updatedUser.getFirstName(),
                    updatedUser.getLastName()
            );
        }

       throw new IllegalArgumentException("Failed to change role. Make sure Id is correct and roles are either 'employee' or 'manager'");
    }
}
