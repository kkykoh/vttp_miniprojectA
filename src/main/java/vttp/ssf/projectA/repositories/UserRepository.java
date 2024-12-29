package vttp.ssf.projectA.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.projectA.Model.User;

@Repository
public class UserRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        redisTemplate.opsForValue().set(username, password);
    }

    public String findPasswordByUsername(String username) {
        return (String) redisTemplate.opsForValue().get(username);
    }

    public void delete(String username) {
        redisTemplate.delete(username);
    }

    public boolean exists(String username) {
        return redisTemplate.hasKey(username);
    }
}
