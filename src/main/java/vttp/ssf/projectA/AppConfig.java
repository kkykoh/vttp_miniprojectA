package vttp.ssf.projectA;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AppConfig {

   private final Logger logger = Logger.getLogger(AppConfig.class.getName());

   // Get all the redis configuration into the class
   @Value("${spring.data.redis.host}")
   private String redisHost;

   @Value("${spring.data.redis.port}")
   private int redisPort;

   @Value("${spring.data.redis.database}")
   private int redisDatabase;

   @Value("${spring.data.redis.username}")
   private String redisUsername;

   @Value("${spring.data.redis.password}")
   private String redisPassword;

   @Bean("redis-0")
   public RedisTemplate<String, Object> createRedisTemplateObject() {
      
      RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
      // Sets the database - select 0
      config.setDatabase(redisDatabase);
      if (!redisUsername.trim().equals("")) {
         logger.info(">>>> Setting Redis username and password");
         config.setUsername(redisUsername);
         config.setPassword(redisPassword);
      }

      JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();

      JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
      jedisFac.afterPropertiesSet();

      RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
      redisTemplate.setConnectionFactory(jedisFac);
      redisTemplate.setKeySerializer(new StringRedisSerializer());
      redisTemplate.setHashKeySerializer(new StringRedisSerializer());
      return redisTemplate;
   }

}