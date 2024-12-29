package vttp.ssf.projectA.Model;

//import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RecipeResponse {

    private List<Recipe> meals;

    public RecipeResponse() {}

    public RecipeResponse(List<Recipe> meals) {
        this.meals = meals;
    }

    public List<Recipe> getMeals() {
        return meals;
    }

    public void setMeals(List<Recipe> meals) {
        this.meals = meals;
    }
}
