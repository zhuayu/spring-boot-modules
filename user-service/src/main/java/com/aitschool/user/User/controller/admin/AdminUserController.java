package com.aitschool.user.User.controller.admin;

import com.aitschool.common.response.CommonResponse;
import com.aitschool.user.User.request.UserStoreRequest;
import com.aitschool.user.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping(path="/admin/users")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @PostMapping(path="")
    public @ResponseBody CommonResponse<Object> store(@RequestBody @Valid UserStoreRequest req) {
        return new CommonResponse<>(userService.store(req));
    }

    @GetMapping(path="")
    @ResponseBody
    public CommonResponse<Object> index(Pageable request) {
        return new CommonResponse<>(userService.index(request));
    }
}
