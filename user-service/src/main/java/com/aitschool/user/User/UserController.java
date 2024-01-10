package com.aitschool.user.User;

import com.aitschool.user.Common.exception.BusinessException;
import com.aitschool.user.Common.response.CommonResponse;
import com.aitschool.user.Common.response.PageJPAResponse;
import com.aitschool.user.User.request.UserStoreRequest;
import com.aitschool.user.User.response.UserIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="users")
    public @ResponseBody CommonResponse<Object> store(@RequestBody @Valid UserStoreRequest req) {
        if (userRepository.existsByPhone(req.getPhone())) {
            throw new BusinessException("手机号已注册了 ！ 🙅");
        }
        User userToSave = new User();
        userToSave.setPhone(req.getPhone());
        User savedUser = userRepository.save(userToSave);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", savedUser.getId());

        return new CommonResponse<>(0, "用户添加成功 🙆", responseData);
    }

    @GetMapping(path = "users")
    @ResponseBody
    public CommonResponse<PageJPAResponse<UserIndexResponse>> index(Pageable request) {
        Page<UserIndexResponse> userIndexResponses = userRepository.findAll(request)
                .map(UserIndexResponse::fromUser);
        return new CommonResponse<>(PageJPAResponse.of(
                userIndexResponses.getContent(),
                userIndexResponses.getTotalElements(),
                userIndexResponses.getNumberOfElements(),
                userIndexResponses.getSize(),
                userIndexResponses.getNumber(),
                userIndexResponses.getTotalPages()
        ));
    }
}
