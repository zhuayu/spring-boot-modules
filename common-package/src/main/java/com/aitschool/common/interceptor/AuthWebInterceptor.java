package com.aitschool.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aitschool.common.annotation.NotAuth;
import com.aitschool.common.context.UserInfoContext;
import com.aitschool.common.exception.BusinessException;
import com.aitschool.common.response.UserInfoResponse;
import com.aitschool.common.util.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
public class AuthWebInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(AuthWebInterceptor.class);

    @Autowired
    private Jwt jwt;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 检查方法上是否标注了 @NotAuth 注解
            if (method.isAnnotationPresent(NotAuth.class)) {
                // 如果标注了 @NotAuth 注解，直接放行
                return true;
            }
        }

        String authorizationHeader = request.getHeader("Authorization");
        if (StrUtil.isBlank(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new BusinessException("Invalid or missing Authorization token");
        }
        String token = authorizationHeader.substring(7);
        if (!jwt.validate(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new BusinessException("Invalid token");
        }

        JSONObject tokenUserInfo = jwt.decode(token);
        UserInfoResponse userInfo = JSONUtil.toBean(tokenUserInfo, UserInfoResponse.class);
        UserInfoContext.setUserInfo(userInfo);

        return true;
    }
}
