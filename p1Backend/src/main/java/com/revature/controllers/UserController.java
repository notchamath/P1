package com.revature.controllers;

import com.revature.aspects.ManagerOnly;
import com.revature.models.DTOs.OutgoingReimbDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register User
    @PostMapping
    public ResponseEntity<OutgoingUserDTO> registerUser(@RequestBody User user){
        return ResponseEntity.ok(userService.registerUser(user));
    }

    //Get all users
    @ManagerOnly
    @GetMapping
    public ResponseEntity<List<OutgoingUserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //Get user their own reimbursements, can filter by status of reimbursement
    @GetMapping("/{userId}/reimbursements")
    public ResponseEntity<List<OutgoingReimbDTO>> getUserReimbursements(@PathVariable("userId") int userId,
                                                                        @RequestParam(value = "status",
                                                                        required = false) String status,
                                                                        HttpSession session
                                                                        ){
        int loggedInUserId = (int) session.getAttribute("userId");
        return ResponseEntity.ok(userService.getUserReimbursements(userId, status, loggedInUserId));
    }

    // Delete User
    @ManagerOnly
    @DeleteMapping("/{userId}")
    public ResponseEntity<OutgoingUserDTO> deleteUser(@PathVariable int userId){
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    //Update User role
    @ManagerOnly
    @PatchMapping("/{userId}/role")
    ResponseEntity<OutgoingUserDTO> updateUserRole(@PathVariable int userId, @RequestBody Map<String, Object> role){
        String newRole = (String) role.get("role");

        return ResponseEntity.ok(userService.updateUserRole(userId, newRole));
    }
}
