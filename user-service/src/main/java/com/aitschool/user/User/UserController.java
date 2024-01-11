package com.aitschool.user.User;

import com.aitschool.user.Common.response.CommonResponse;
import com.aitschool.user.Common.response.PageJPAResponse;
import com.aitschool.user.User.request.UserStoreRequest;
import com.aitschool.user.User.response.UserIndexResponse;
import com.aitschool.user.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping(path="/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path="users")
    public @ResponseBody CommonResponse<Object> store(@RequestBody @Valid UserStoreRequest req) {
        return userService.store(req);
    }

    @GetMapping(path = "users")
    @ResponseBody
    public CommonResponse<PageJPAResponse<UserIndexResponse>> index(Pageable request) {
        return userService.index(request);
    }
}
