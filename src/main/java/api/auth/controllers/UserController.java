package api.auth.controllers;

import api.auth.models.SearchRequest;
import api.auth.models.UserModel;
import api.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<List<UserModel>> getUsers(@RequestBody SearchRequest request) {
        try {
            List<UserModel> users = userService.getUsers(request);
            if (users == null || users.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonList(new UserModel()));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> getUsers(
            @RequestParam("type") String type,
            @RequestParam(value = "value", required = false) String value) throws Exception {
        Object result = userService.getUserType(type, value);
        return ResponseEntity.ok(result);
    }

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id.intValue());
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error deleting user: " + e.getMessage());
        }
    }
}