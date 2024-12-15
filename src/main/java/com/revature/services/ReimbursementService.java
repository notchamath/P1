package com.revature.services;

import com.revature.models.DTOs.IncomingReimbDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReimbursementService {

    private final ReimbursementDAO reimbursementDAO;
    private final UserDAO userDAO;

    @Autowired
    public ReimbursementService(ReimbursementDAO reimbursementDAO, UserDAO userDAO) {
        this.reimbursementDAO = reimbursementDAO;
        this.userDAO = userDAO;
    }

    public Reimbursement createReimbursement(IncomingReimbDTO reimbDTO){

        if(reimbDTO.getDescription() == null || reimbDTO.getDescription().isBlank()){
            throw new IllegalArgumentException("Reimbursement Description cannot be blank or null");
        }

        if(reimbDTO.getAmount() <= 0 ){
            throw new IllegalArgumentException("Reimbursement Amount cannot be less than or equal to $0");
        }

        if(reimbDTO.getUserId() == null){
            throw new IllegalArgumentException("Reimbursement UserId cannot be blank or null");
        }

        Reimbursement reimbursement = new Reimbursement(
                0,
                reimbDTO.getDescription(),
                reimbDTO.getAmount(),
                reimbDTO.getStatus(),
                null
        );

        Optional<User> user = userDAO.findById(reimbDTO.getUserId());

        if(user.isEmpty()){
            throw new IllegalArgumentException("No user is found by the Id: " + reimbDTO.getUserId());
        } else {
            reimbursement.setUser(user.get());
            return reimbursementDAO.save(reimbursement);
        }

    }
}
