package com.revature.services;

import com.revature.models.DTOs.IncomingReimbDTO;
import com.revature.models.DTOs.OutgoingReimbDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReimbursementService {

    private final ReimbursementDAO reimbursementDAO;
    private final UserDAO userDAO;

    @Autowired
    public ReimbursementService(ReimbursementDAO reimbursementDAO, UserDAO userDAO) {
        this.reimbursementDAO = reimbursementDAO;
        this.userDAO = userDAO;
    }

    public OutgoingReimbDTO createReimbursement(IncomingReimbDTO reimbDTO, int loggedInUserId){

        if(reimbDTO.getDescription() == null || reimbDTO.getDescription().isBlank()){
            throw new IllegalArgumentException("Reimbursement Description cannot be blank or null");
        }

        if(reimbDTO.getAmount() <= 0 ){
            throw new IllegalArgumentException("Reimbursement Amount cannot be less than or equal to $0");
        }

        if(reimbDTO.getUserId() == null){
            throw new IllegalArgumentException("Reimbursement UserId cannot be blank or null");
        }

        if(reimbDTO.getUserId() != loggedInUserId){
            throw new IllegalArgumentException("You cannot create a Reimbursement for a different user. Enter the correct userId");
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
            Reimbursement savedReimb = reimbursementDAO.save(reimbursement);
            return new OutgoingReimbDTO(
                    savedReimb.getReimbId(),
                    savedReimb.getDescription(),
                    savedReimb.getAmount(),
                    savedReimb.getStatus(),
                    savedReimb.getUser().getUserId()
            );
        }
    }

    public List<OutgoingReimbDTO> getAllReimbursements(String status){
        List<Reimbursement> reimbs = reimbursementDAO.findAll(Sort.by("reimbId").ascending());
        List<OutgoingReimbDTO> outgoingReimbs = new ArrayList<>();

        for(Reimbursement reimb: reimbs){
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
            return getAllReimbursementsByStatus(outgoingReimbs, status);
        }
    }

    public List<OutgoingReimbDTO> getAllReimbursementsByStatus(List<OutgoingReimbDTO> outgoingReimbs, String status){
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

    public OutgoingReimbDTO updateReimbDescription(int reimbId, String descriptionText, int loggedInUserId){
        Optional<Reimbursement> reimbOptional = reimbursementDAO.findById(reimbId);

        if(reimbOptional.isEmpty()){
            throw new IllegalArgumentException("No Reimbursement found by the ID: " + reimbId);
        }

        if(reimbOptional.get().getUser().getUserId() != loggedInUserId){
            throw new IllegalArgumentException("The reimbursement entered does not belong to the logged in user with ID: " + loggedInUserId);
        }

        if(!reimbOptional.get().getStatus().equalsIgnoreCase("pending")){
            throw new IllegalArgumentException("This reimbursement is has already been: " + reimbOptional.get().getStatus());
        }

        if(descriptionText != null && !descriptionText.isBlank() && descriptionText.length() <= 255){

            reimbOptional.get().setDescription(descriptionText);
            Reimbursement savedReimb = reimbursementDAO.save(reimbOptional.get());
            return new OutgoingReimbDTO(
                    savedReimb.getReimbId(),
                    savedReimb.getDescription(),
                    savedReimb.getAmount(),
                    savedReimb.getStatus(),
                    savedReimb.getUser().getUserId()
            );
        } else {
            throw new IllegalArgumentException("Reimbursement could not be updated. Please check Description and ID");
        }
    }

    public OutgoingReimbDTO updateReimbStatus(int reimbId, String status){
        Optional<Reimbursement> reimb = reimbursementDAO.findById(reimbId);

        if(reimb.isEmpty()) throw new IllegalArgumentException("No reimbursement found by Id: " + reimbId);

        if(!reimb.get().getStatus().equalsIgnoreCase("pending")){
            throw new IllegalArgumentException("This reimbursement is no longer pending. It has been: " + reimb.get().getStatus());
        }

        if(status != null && !status.isBlank() &&
                (status.equalsIgnoreCase("pending")
                || status.equalsIgnoreCase("approved")
                || status.equalsIgnoreCase("denied")))
        {
            reimb.get().setStatus(status);
            Reimbursement savedReimb = reimbursementDAO.save(reimb.get());
            return new OutgoingReimbDTO(
                    savedReimb.getReimbId(),
                    savedReimb.getDescription(),
                    savedReimb.getAmount(),
                    savedReimb.getStatus(),
                    savedReimb.getUser().getUserId()
            );
        } else {
            throw new IllegalArgumentException("Reimbursement could not be updated. Please check status and ID");
        }
    }
}
