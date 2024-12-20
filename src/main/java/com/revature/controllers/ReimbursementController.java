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

    //Get All Reimbursements
    @GetMapping
    public ResponseEntity<List<Reimbursement>> getAllReimbursements(){
        return ResponseEntity.ok(reimbursementService.getAllReimbursements());
    }

    //Update Reimbursement Description
    @PatchMapping("/{reimbId}/description")
    ResponseEntity<Reimbursement> updateReimbDescription(@PathVariable int reimbId, @RequestBody Map<String, Object> newReimbDetails){
        String descriptionText = (String) newReimbDetails.get("descriptionText");

        return ResponseEntity.ok(reimbursementService.updateReimbDescription(reimbId, descriptionText));
    }
}
