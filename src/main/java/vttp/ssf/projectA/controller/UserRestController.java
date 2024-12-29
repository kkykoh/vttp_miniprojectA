package vttp.ssf.projectA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.projectA.services.UserService;

@RestController
@RequestMapping("/authenticate")
public class UserRestController {
    
     @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password) {
        try {
            userService.registerUser(username, password);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // @PostMapping("/register")
    // public ResponseEntity<Void> register(@RequestParam String username, @RequestParam String password, HttpSession session) {
    //     try {
    //         userService.registerUser(username, password);
    //         return ResponseEntity.status(HttpStatus.FOUND) // Status 302 for redirection
    //                 .header("Location", "/homepage") // Redirect to the homepage
    //                 .build();
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    //     }
    // }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        boolean isAuthenticated = userService.authenticateUser(username, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // @PostMapping("/login")
    // public ResponseEntity<Void> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
    //     boolean isAuthenticated = userService.authenticateUser(username, password);
    //     if (isAuthenticated) {
    //         return ResponseEntity.status(HttpStatus.FOUND) // Status 302 for redirection
    //                 .header("Location", "/homepage") // Redirect to the homepage
    //                 .build();
    //     } else {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    //     }
    // }
}

