
package vttp.ssf.projectA.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import vttp.ssf.projectA.Model.Meal;
import vttp.ssf.projectA.Model.Recipe;
import vttp.ssf.projectA.Model.RecipeResponse;
import vttp.ssf.projectA.repositories.RecipeRepository;

@Service
public class RecipeService {

    @Value("${themealdb.api.url}")
    private String mealDbUrl;

    @Autowired
    private RecipeRepository recipeRepository;

    private <T> T fetchFromApi(String url, TypeReference<T> typeReference) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String jsonResponse = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching data from API: " + e.getMessage());

        }
    }
    
    private List<Recipe> fetchRecipesFromAPI(String ingredient) {
        String filterIngredienturl = mealDbUrl + "/filter.php?i=" + ingredient;
        RestTemplate restTemplate = new RestTemplate();
        RecipeResponse response = restTemplate.getForObject(filterIngredienturl, RecipeResponse.class);

        return response != null ? response.getMeals() : List.of();
    }

    public Meal searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be null or empty.");
        }

        String url = mealDbUrl + "/search.php?s=" + name;
        // RestTemplate restTemplate = new RestTemplate();
        // RecipeResponse response = restTemplate.getForObject(url,RecipeResponse.class);
        RecipeResponse response = fetchFromApi(url, new TypeReference<>() {
        });

        // return response != null ? response.getMeals() : List.of();

        try {
            RestTemplate restTemplate = new RestTemplate();
            String jsonResponse = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(jsonResponse);

            JsonNode meals = responseNode.path("meals");

            if (meals.isArray() && meals.size() > 0) {
                JsonNode mealNode = meals.get(0); 
                Meal meal = new Meal();
                meal.setIdMeal(mealNode.path("idMeal").asText());
                meal.setMealName(mealNode.path("strMeal").asText());
                meal.setMealThumbnail(mealNode.path("strMealThumb").asText());
                meal.setInstructions(mealNode.path("strInstructions").asText());
                meal.setYoutubeLink(mealNode.path("strYoutube").asText());

                for (int i = 1; i <= 20; i++) {
                    String ingredient = mealNode.path("strIngredient" + i).asText();
                    String measurement = mealNode.path("strMeasure" + i).asText();
                    if (!ingredient.isEmpty()) {
                        meal.getIngredients().add(ingredient);
                    }
                    if (!measurement.isEmpty()) {
                        meal.getMeasurements().add(measurement);
                    }
                }

                System.out.println("Request URL: " + url);
                System.out.println("API Response: " + jsonResponse);

                return meal;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Recipe> findRecipesByIngredient(String ingredient) {
        if (ingredient == null || ingredient.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient cannot be null or empty.");
        }
        
        String url = mealDbUrl + "/filter.php?i=" + ingredient;

        
        RestTemplate restTemplate = new RestTemplate();
        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);

        
        return response != null ? response.getMeals() : List.of();
    }

    public Meal getRecipeById(String idMeal) {
        String url = mealDbUrl + "/lookup.php?i=" + idMeal;

        try {
            RestTemplate restTemplate = new RestTemplate();
            String jsonResponse = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(jsonResponse);

            JsonNode meals = responseNode.path("meals");

            if (meals.isArray() && meals.size() > 0) {
                JsonNode mealNode = meals.get(0);
                Meal meal = new Meal();
                meal.setIdMeal(mealNode.path("idMeal").asText());
                meal.setMealName(mealNode.path("strMeal").asText());
                meal.setMealThumbnail(mealNode.path("strMealThumb").asText());
                meal.setInstructions(mealNode.path("strInstructions").asText());
                meal.setYoutubeLink(mealNode.path("strYoutube").asText());

                
                for (int i = 1; i <= 20; i++) {
                    String ingredient = mealNode.path("strIngredient" + i).asText();
                    String measurement = mealNode.path("strMeasure" + i).asText();
                    if (!ingredient.isEmpty()) {
                        meal.getIngredients().add(ingredient);
                    }
                    if (!measurement.isEmpty()) {
                        meal.getMeasurements().add(measurement);
                    }
                }

                System.out.println("Request URL: " + url);
                System.out.println("API Response: " + jsonResponse);

                return meal;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; 
    }

    public void addFavorite(String username, String idMeal) {
        recipeRepository.addFavorite(username, idMeal);
    }

    public List<Object> getFavorites(String username) {
        return recipeRepository.getFavorites(username);
    }

}
