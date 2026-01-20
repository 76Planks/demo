package com.example.demo.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfiguration {


    // Configure RedisTemplate is used to interact with Redis data store directly

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        ObjectMapper objectMapper = new ObjectMapper();

        GenericJacksonJsonRedisSerializer jsonRedisSerializer = new GenericJacksonJsonRedisSerializer(objectMapper);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;

    }

    // Configure CacheManager to manage cache with Redis as the underlying store used by Spring's caching abstraction
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        ObjectMapper objectMapper = new ObjectMapper();
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJacksonJsonRedisSerializer(objectMapper))
                )
                .disableCachingNullValues();


        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(configuration)
                .build();
    }
}
