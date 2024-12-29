package vttp.ssf.projectA.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meal implements Serializable {

    @JsonProperty("idMeal")
    private String idMeal;

    @JsonProperty("strMeal")
    private String mealName;

    @JsonProperty("strMealThumb")
    private String mealThumbnail;

    @JsonProperty("strInstructions")
    private String instructions;

    @JsonProperty("strYoutube")
    private String youtubeLink;

    private List<String> ingredients = new ArrayList<>(); 
    private List<String> measurements = new ArrayList<>();

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealThumbnail() {
        return mealThumbnail;
    }

    public void setMealThumbnail(String mealThumbnail) {
        this.mealThumbnail = mealThumbnail;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<String> measurements) {
        this.measurements = measurements;
    }
}