package vttp.ssf.projectA.Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe implements Serializable {

    @JsonProperty("idMeal")
    private String idMeal;

    @JsonProperty("strMeal")
    private String mealName;

    @JsonProperty("strMealThumb")
    private String mealThumbnail;

    public Recipe() {}

    public Recipe(String idMeal, String mealName, String mealThumbnail) {
        this.idMeal = idMeal;
        this.mealName = mealName;
        this.mealThumbnail = mealThumbnail;
    }

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
}
