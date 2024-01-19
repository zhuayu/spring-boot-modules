package com.aitschool.user.User.service;

import com.aitschool.common.exception.BusinessException;
import com.aitschool.common.response.PageJPAResponse;
import com.aitschool.user.User.model.User;
import com.aitschool.user.User.repository.UserRepository;
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

    public Object store(UserStoreRequest req) {
        if (userRepository.existsByPhone(req.getPhone())) {
            throw new BusinessException("手机号已注册了 ！ 🙅");
        }
        User userToSave = new User();
        userToSave.setPhone(req.getPhone());
        User savedUser = userRepository.save(userToSave);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", savedUser.getId());

        return responseData;
    }

    public PageJPAResponse index(Pageable request) {
        Page<UserIndexResponse> userIndexResponses = userRepository.findAll(request)
                .map(UserIndexResponse::fromUser);
        return PageJPAResponse.of(
                userIndexResponses.getContent(),
                userIndexResponses.getTotalElements(),
                userIndexResponses.getNumberOfElements(),
                userIndexResponses.getSize(),
                userIndexResponses.getNumber(),
                userIndexResponses.getTotalPages()
        );
    }
}
