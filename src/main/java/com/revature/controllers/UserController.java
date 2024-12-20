package com.revature.controllers;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register User
    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User registeredUser = userService.registerUser(user);

        return ResponseEntity.ok(registeredUser);
    }

    //Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //Get user their own reimbursements, can filter by status of reimbursement
    @GetMapping("/{userId}/reimbursements")
    public ResponseEntity<List<Reimbursement>> getUserReimbursements(@PathVariable("userId") int userId, @RequestParam(value = "status", required = false) String status){
        return ResponseEntity.ok(userService.getUserReimbursements(userId, status));
    }

    // Delete User
    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable int userId){
        User deletedUser = userService.deleteUser(userId);

        return ResponseEntity.ok(deletedUser);
    }

    //Update User role
    @PatchMapping("/{userId}/role")
    ResponseEntity<User> updateUserRole(@PathVariable int userId, @RequestBody Map<String, Object> role){
        String newRole = (String) role.get("role");

        return ResponseEntity.ok(userService.updateUserRole(userId, newRole));
    }
}
