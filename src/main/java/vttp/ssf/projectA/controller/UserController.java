package vttp.ssf.projectA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.projectA.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/login")

    public String loginUser(HttpSession session, Model model, 
                            @RequestParam String username, 
                            @RequestParam String password) {
        // Forward request to authentication logic in UserRestController
        // Redirect to user homepage after login
        boolean isAuthenticated = userService.authenticateUser(username, password);
        if (isAuthenticated) {
            session.setAttribute("username", username);
            System.out.println(username);
            return "redirect:/homepage"; 
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login"; 
        }
    }

    @PostMapping("/register")
    public String registerUser(Model model, 
                               @RequestParam String username, 
                               @RequestParam String password) {
        try {
            userService.registerUser(username, password);
            return "redirect:/users/login"; 
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register"; 
        }
    }

    
}
