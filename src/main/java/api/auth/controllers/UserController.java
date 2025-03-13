package api.auth.controllers;

import api.auth.models.UserModel;
import api.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserModel userModel) {
        userService.registerUser(userModel);
        return ResponseEntity.ok("Successful operation");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted user");
    }
}