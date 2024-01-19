package com.aitschool.order.Order.controller.web;

import com.aitschool.common.annotation.NotAuth;
import com.aitschool.common.context.UserInfoContext;
import com.aitschool.common.response.CommonResponse;
import com.aitschool.common.response.UserInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/web/orders")
public class WebOrderController {

    @GetMapping(path="/user-info")
    public @ResponseBody CommonResponse<Object> userInfo(HttpServletRequest request) {
        UserInfoResponse userInfo = UserInfoContext.getUserInfo();
        return new CommonResponse<>(userInfo);
    }

    @NotAuth
    @GetMapping(path="/not-auth")
    public @ResponseBody CommonResponse<Object> notAuth() {
        return new CommonResponse<>(null);
    }
}
