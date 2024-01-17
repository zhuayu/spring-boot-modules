package com.aitschool.user.User.controller.web;

import com.aitschool.common.response.CommonResponse;
import com.aitschool.user.User.request.UserStoreRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/web/users")
public class UserController {
    @PostMapping(path="/user-info")
    public @ResponseBody CommonResponse<Object> userInfo(@RequestBody @Valid UserStoreRequest req) {
        return new CommonResponse<>(null);
    }
}
