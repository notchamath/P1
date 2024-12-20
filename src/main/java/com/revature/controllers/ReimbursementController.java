package com.revature.controllers;

import com.revature.models.DTOs.IncomingReimbDTO;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
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
    public ResponseEntity<Reimbursement> createReimbursement(@RequestBody IncomingReimbDTO reimbDTO){
        Reimbursement reimbursement = reimbursementService.createReimbursement(reimbDTO);

        return ResponseEntity.ok(reimbursement);
    }

    //Get All Reimbursements, can filter by status of reimbursement
    @GetMapping
    public ResponseEntity<List<Reimbursement>> getAllReimbursements(@RequestParam(value = "status", required = false) String status){
        return ResponseEntity.ok(reimbursementService.getAllReimbursements(status));
    }

    //Update Reimbursement Description if it's still pending
    @PatchMapping("/{reimbId}/description")
    ResponseEntity<Reimbursement> updateReimbDescription(@PathVariable int reimbId, @RequestBody Map<String, Object> newReimbDetails){
        String descriptionText = (String) newReimbDetails.get("descriptionText");

        return ResponseEntity.ok(reimbursementService.updateReimbDescription(reimbId, descriptionText));
    }

    //Update Reimbursement Status
    @PatchMapping("/{reimbId}/status")
    ResponseEntity<Reimbursement> updateReimbStatus(@PathVariable int reimbId, @RequestBody Map<String, Object> newReimbDetails){
        String status = (String) newReimbDetails.get("status");

        return ResponseEntity.ok(reimbursementService.updateReimbStatus(reimbId, status));
    }
}
