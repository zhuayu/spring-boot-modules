package com.aitschool.user.User.controller.web;

import com.aitschool.common.annotation.NotAuth;
import com.aitschool.common.context.UserInfoContext;
import com.aitschool.common.response.CommonResponse;
import com.aitschool.common.response.UserInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/web/users")
public class WebUserController {
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
