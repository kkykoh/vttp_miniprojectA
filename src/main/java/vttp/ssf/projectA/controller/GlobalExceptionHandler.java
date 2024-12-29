package vttp.ssf.projectA.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("currentTime", LocalDateTime.now().format(formatter));
        model.addAttribute("errorType", ex.getClass().getSimpleName());
        model.addAttribute("errorMessage", ex.getMessage());
        
        
        return "error";
    }
    
}
