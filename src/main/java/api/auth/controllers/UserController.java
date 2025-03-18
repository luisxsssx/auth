package api.auth.controllers;

import api.auth.models.UserModel;
import api.auth.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

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

    @PostMapping("/status/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable("id") Long id) {
        try {
            userService.changeStatus(id.intValue());
            return ResponseEntity.ok("User status changed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error changing user status: " + e.getMessage());
        }
    }
}