package com.revature.controllers;

import com.revature.aspects.ManagerOnly;
import com.revature.models.DTOs.IncomingReimbDTO;
import com.revature.models.DTOs.OutgoingReimbDTO;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reimbursements")
@CrossOrigin
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }

    //Create Reimbursement
    @PostMapping
    public ResponseEntity<OutgoingReimbDTO> createReimbursement(@RequestBody IncomingReimbDTO reimbDTO, HttpSession session){
        int loggedInUserId = (int) session.getAttribute("userId");
        OutgoingReimbDTO reimbursement = reimbursementService.createReimbursement(reimbDTO, loggedInUserId);

        return ResponseEntity.ok(reimbursement);
    }

    //Get All Reimbursements, can filter by status of reimbursement
    @ManagerOnly
    @GetMapping
    public ResponseEntity<List<OutgoingReimbDTO>> getAllReimbursements(@RequestParam(value = "status", required = false) String status){
        return ResponseEntity.ok(reimbursementService.getAllReimbursements(status));
    }

    //Update Reimbursement Description if it's still pending
    @PatchMapping("/{reimbId}/description")
    ResponseEntity<OutgoingReimbDTO> updateReimbDescription(@PathVariable int reimbId, @RequestBody Map<String, Object> newReimbDetails, HttpSession session){
        String descriptionText = (String) newReimbDetails.get("descriptionText");
        int loggedInUserId = (int) session.getAttribute("userId");

        return ResponseEntity.ok(reimbursementService.updateReimbDescription(reimbId, descriptionText, loggedInUserId));
    }

    //Update Reimbursement Status
    @ManagerOnly
    @PatchMapping("/{reimbId}/status")
    ResponseEntity<OutgoingReimbDTO> updateReimbStatus(@PathVariable int reimbId, @RequestBody Map<String, Object> newReimbDetails){
        String status = (String) newReimbDetails.get("status");

        return ResponseEntity.ok(reimbursementService.updateReimbStatus(reimbId, status));
    }
}
