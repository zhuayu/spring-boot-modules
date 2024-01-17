package com.aitschool.common.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.GlobalBouncyCastleProvider;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Jwt {

    private static final Logger LOG = LoggerFactory.getLogger(Jwt.class);

    @Value("${jwt.secret}")
    private String key;

    private void setKey(String key) {
        this.key = key;
    }

    public String encode(long id) {
        return encode(id, "web", false);
    }

    public String encode(long id, String platform, boolean remember) {
        LOG.info("开始生成JWT token，key：{}", key);
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        DateTime now = DateTime.now();
        DateTime expTime = remember ? now.offsetNew(DateField.DAY_OF_MONTH, 30) : now.offsetNew(DateField.DAY_OF_MONTH, 1);
        DateTime expTimeAt = DateUtil.endOfDay(expTime).offsetNew(DateField.HOUR, 4);
        Map<String, Object> payload = new HashMap<>();
        payload.put(JWTPayload.ISSUED_AT, now);
        payload.put(JWTPayload.EXPIRES_AT, expTimeAt);
        payload.put(JWTPayload.NOT_BEFORE, now);
        payload.put("id", id);
        payload.put("sub", id);
        payload.put("type", "user");
        payload.put("platform", platform);
        String token = JWTUtil.createToken(payload, key.getBytes());
        LOG.info("生成JWT token：{}", token);
        return token;
    }

    public boolean validate(String token) {
        LOG.info("开始JWT token校验，token：{}", token);
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        boolean validate = jwt.validate(0);
        LOG.info("JWT token校验结果：{}", validate);
        return validate;
    }

    public JSONObject decode(String token) {
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        LOG.info("根据token获取原始内容：{}", payloads);
        return payloads;
    }

    public static void main(String[] args) {
        Jwt j = new Jwt();
        j.setKey("born2code");
        j.encode(123666L);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEyMzY2NiwibmJmIjoxNzA1NDc2NTExLCJpZCI6MTIzNjY2LCJleHAiOjE3MDU2MDc5OTksInR5cGUiOiJ1c2VyIiwiaWF0IjoxNzA1NDc2NTExLCJwbGF0Zm9ybSI6IndlYiJ9.TSV0EX-JIMs8NciDLHApgK3onHjy93NyzQMJT-QNBzI";
        j.validate(token);
        j.decode(token);
    }
}
