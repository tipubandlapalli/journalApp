package net.engineeringdigest.journalapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate; // Use String for key and value for simplicity
    private final ObjectMapper objectMapper;

    public <VALUE> void set(String keyString, VALUE value) throws JsonProcessingException {
        String valueString = objectMapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(keyString, valueString, 300);
    }

    public <T> T get(String keyString, Class<T> targetClass) throws IOException {
        String o = redisTemplate.opsForValue().get(keyString);

        if (o != null) {
            return objectMapper.readValue(o, targetClass);
        }

        return null;
    }
}