package vttp.ssf.projectA.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.projectA.Model.Meal;
import vttp.ssf.projectA.Model.Recipe;
import vttp.ssf.projectA.services.RecipeService;


@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController {

    @Autowired
    private RecipeService recipeService;

    private static final Logger logger = LoggerFactory.getLogger(RecipeRestController.class);

    @GetMapping("/{ingredient}")
    public List<Recipe> getRecipesByIngredient(@RequestParam String ingredient) {
        //forward the request to the external API (MealDB API)
        return recipeService.findRecipesByIngredient(ingredient);
    }

    @GetMapping("/details/{idMeal}")
    public ResponseEntity<Meal> getRecipeById(@PathVariable String idMeal) {
        System.out.println("Fetching recipe with ID: " + idMeal);
        Meal meal = recipeService.getRecipeById(idMeal);
        if (meal != null) {
            return ResponseEntity.ok(meal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Meal> getRecipeByName(@PathVariable String name) {
        System.out.println("Fetching recipe with name: " + name);
        Meal meal = recipeService.searchByName(name);
        if (meal != null) {
            return ResponseEntity.ok(meal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}