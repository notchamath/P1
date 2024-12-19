package com.revature.controllers;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User registeredUser = userService.registerUser(user);

        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/{userId}/reimbursements")
    public ResponseEntity<List<Reimbursement>> getUserReimbursements(@PathVariable("userId") int userId){

        return ResponseEntity.ok(userService.getUserReimbursements(userId));
    }

    @GetMapping("/{userId}/reimbursements/pending")
    public ResponseEntity<List<Reimbursement>> getUserPendingReimbursements(@PathVariable("userId") int userId){

        return ResponseEntity.ok(userService.getUserPendingReimbursements(userId));
    }

}
