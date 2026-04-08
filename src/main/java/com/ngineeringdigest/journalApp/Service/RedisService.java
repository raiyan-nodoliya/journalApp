package com.ngineeringdigest.journalApp.Service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String key, Class<T> entityClass) {
        try {

            String value = redisTemplate.opsForValue().get(key);

            if (value == null) {
                return null;
            }

            return mapper.readValue(value, entityClass);

        } catch (Exception e) {
            log.error("Exception while getting from Redis: ", e);
            return null;
        }
    }

    public void set(String key, Object value, Long ttl) {
        try {

            String jsonValue = mapper.writeValueAsString(value);

            redisTemplate
                    .opsForValue()
                    .set(key, jsonValue, ttl, TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error("Exception while setting Redis value: ", e);
        }
    }
}