package vttp.ssf.projectA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/homepage")
    public String homePage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username); 
        }
        return "index";
    }

    @GetMapping(path={"/","/users/login"})
    public String login(HttpSession session, Model model){
        // if (session.getAttribute("username") != null) {
        //     return "redirect:/homepage";
        //}
        return "login";
    }

    @GetMapping("/users/register")
    public String register(Model model) {
        return "register";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }

    @GetMapping("/searchName")
    public String searchName() {
        return "searchName";
    }

    @GetMapping("/history")
    public String historyPage() {
        return "history";
    }

    @GetMapping("/testError")
    public String causeError() {
        throw new RuntimeException("This is a test error.");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

}
