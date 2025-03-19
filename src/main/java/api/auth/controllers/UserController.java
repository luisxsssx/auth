package api.auth.controllers;

import api.auth.models.UserModel;
import api.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user")
public class UserController {
    @Autowired
    private UserService userService;

    // user role
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserModel userModel) {
        try {
            userService.registerUser(userModel);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error registering user: " + e.getMessage());
        }
    }
}