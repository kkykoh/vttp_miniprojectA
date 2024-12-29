
package vttp.ssf.projectA.repositories;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.projectA.Model.Recipe;
import vttp.ssf.projectA.Model.RecipeResponse;

@Repository
public class RecipeRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // private static final String INGREDIENTS_KEY_PREFIX = "recipes:ingredient:";
    private static final String FAVORITES_KEY = "favorites : ";
    // private static final String RECIPE_ID_PREFIX = "recipes:id:";

    public void saveUser(String username, String password, List<String> favorites) {
        //String key = USER_KEY_PREFIX + username;  
        String key1 =  "PASSWORD FOR " + username;  
        String key2 = "FAVORITES FPR " + username;  


        // redisTemplate.opsForHash().put(username, "password", password);
        // redisTemplate.opsForHash().put(username, "favorites", favorites);
        redisTemplate.opsForList().set(key1, 0, password);
        redisTemplate.opsForList().set(key2, 0, favorites);
        // Set expiration time for the user data (30 days)
        redisTemplate.expire(username, Duration.ofDays(30));
    }
    
    public String getPassword(String username) {
        //String key = USER_KEY_PREFIX + username;
        return (String) redisTemplate.opsForHash().get(username, "password");
    }


    
    public void addFavorite(String username, String idMeal) {
        String key = FAVORITES_KEY + username; 
        redisTemplate.opsForList().rightPush(key, idMeal);
    }

    public List<Object> getFavorites(String username) {
        String key = FAVORITES_KEY + username;
        return redisTemplate.opsForList().range(key, 0, -1); 
    }

}
