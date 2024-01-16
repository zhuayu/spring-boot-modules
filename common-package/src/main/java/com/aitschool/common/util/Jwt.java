package com.aitschool.common.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.GlobalBouncyCastleProvider;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class Jwt {
    private static final Logger LOG = LoggerFactory.getLogger(Jwt.class);

    private static final String key = "born2code"; // key 之后需要放到 env

    public static String createToken(long id) {
        return createToken(id, "web", false);
    }

    public static String createToken(long id, String platform, boolean remember) {
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

    public static boolean validate(String token) {
        LOG.info("开始JWT token校验，token：{}", token);
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        boolean validate = jwt.validate(0);
        LOG.info("JWT token校验结果：{}", validate);
        return validate;
    }

    public static JSONObject getJSONObject(String token) {
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
        Jwt.createToken(123666L);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEyMzY2NiwibmJmIjoxNzA1NDExMDY4LCJpZCI6MTIzNjY2LCJleHAiOjE3MDU1MjE1OTksInR5cGUiOiJ1c2VyIiwiaWF0IjoxNzA1NDExMDY4LCJwbGF0Zm9ybSI6IndlYiJ9.CQRuvsTRA28xeu0wnfhgn_Tviadq5D2rq0xll2TuYTY";
        validate(token);
        getJSONObject(token);
    }

}
