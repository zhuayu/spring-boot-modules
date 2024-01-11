package com.aitschool.user.User.service;

import com.aitschool.user.Common.exception.BusinessException;
import com.aitschool.user.Common.response.CommonResponse;
import com.aitschool.user.Common.response.PageJPAResponse;
import com.aitschool.user.User.User;
import com.aitschool.user.User.UserRepository;
import com.aitschool.user.User.request.UserStoreRequest;
import com.aitschool.user.User.response.UserIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public CommonResponse<Object> store(UserStoreRequest req) {
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
