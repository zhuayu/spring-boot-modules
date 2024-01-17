package com.aitschool.user.User.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SMSService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final int SMS_CODE_LENGTH = 5;

    public Map<String, String> getSmsCode(String phoneNumber, String scene) {
        String randomCode = generateRandomCode();
        String redisKey = "USER_SERVICE_" + scene + '_' + phoneNumber;
        redisTemplate.opsForValue().set(redisKey, randomCode, 5, TimeUnit.MINUTES);
        Map<String, String> result = new HashMap<>();
        result.put("code", randomCode);
        result.put("key", redisKey);
        return result;
    }


    public boolean checkSmsCode(String code, String phoneNumber, String redisKey) {
        String storedCode = redisTemplate.opsForValue().get(redisKey);
        return code.equals(storedCode);
    }

    private String generateRandomCode() {
        // 生成随机5位数
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SMS_CODE_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
