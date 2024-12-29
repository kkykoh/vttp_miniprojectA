package vttp.ssf.projectA.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.projectA.Model.Meal;
import vttp.ssf.projectA.Model.Recipe;
import vttp.ssf.projectA.services.RecipeService;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/name")
    public String searchByName(@RequestParam String name, Model model, HttpSession session) {
        System.out.println("Fetching recipe with name: " + name);

        Meal meal = recipeService.searchByName(name);
        if (name == null || name.trim().isEmpty()) {
            model.addAttribute("error", "Recipe name cannot be empty.");
            return "errorPage";
        }

        try {
            if (meal == null) {
                model.addAttribute("error", "No meal found with name: " + name);
                return "errorPage";
            }
            model.addAttribute("meal", meal);
            return "recipeDetails"; 
        } catch (Exception e) {
            System.err.println("Error fetching recipe details: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while fetching the recipe details.");
            return "errorPage";
        }
    }

    @GetMapping("/ingredient")
    public String searchByIngredient(@RequestParam String ingredient, Model model, HttpSession session) {
        if (ingredient == null || ingredient.trim().isEmpty()) {
            model.addAttribute("error", "Ingredient cannot be empty.");
            return "error";
        }

        try {
            model.addAttribute("recipes", recipeService.findRecipesByIngredient(ingredient));
        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch recipes.");
            return "error";
        }
        return "recipeList";
    }

    @GetMapping("/details/{idMeal}")
    public String getRecipeDetails(@PathVariable String idMeal, Model model, HttpSession session) {
        System.out.println("Fetching recipe with ID: " + idMeal);

        Meal meal = recipeService.getRecipeById(idMeal);

        if (idMeal == null || idMeal.trim().isEmpty()) {
            model.addAttribute("error", "Meal ID cannot be empty.");
            return "errorPage";
        }

        try {
            if (meal == null) {
                model.addAttribute("error", "No meal found with ID: " + idMeal);
                return "errorPage"; 
            }
            model.addAttribute("meal", meal);
            return "recipeDetails"; 
        } catch (Exception e) {
            System.err.println("Error fetching recipe details: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while fetching the recipe details.");
            return "errorPage";
        }
    
    }

    @PostMapping("/favorites")
    public String addToFavorites(@RequestParam String idMeal, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            model.addAttribute("error", "You must be logged in to add favorites.");
            return "login";
        }

        try {
            recipeService.addFavorite(username, idMeal);
            model.addAttribute("message", "Recipe added to favorites successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add recipe to favorites.");
        }
        return "redirect:/recipes/favorites"; 
    }

    @GetMapping("/favorites")
    public String getFavorites(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            model.addAttribute("error", "You must be logged in to view favorites.");
            return "login";
        }

        try {
            List<Object> favorites = recipeService.getFavorites(username);
            model.addAttribute("favorites", favorites);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch favorites.");
        }
        return "favorites";
    }

}
