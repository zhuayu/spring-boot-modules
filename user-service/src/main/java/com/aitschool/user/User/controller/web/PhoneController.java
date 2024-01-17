package com.aitschool.user.User.controller.web;

import com.aitschool.common.exception.BusinessException;
import com.aitschool.common.response.CommonResponse;
import com.aitschool.common.util.Jwt;
import com.aitschool.user.User.model.User;
import com.aitschool.user.User.repository.UserRepository;
import com.aitschool.user.User.request.PhoneSmsCheckRequest;
import com.aitschool.user.User.request.PhoneSmsCodeRequest;
import com.aitschool.user.User.response.UserIndexResponse;
import com.aitschool.user.User.service.PhoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/web/phone")
public class PhoneController {

    @Autowired
    private Jwt jwt;

    @Autowired
    private PhoneService phoneService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/sms/code")
    @ResponseBody
    public CommonResponse<Object> getSmsCode(@Valid PhoneSmsCodeRequest req) {
        Map<String, String> result = phoneService.getSmsCode(req.getPhone(), "PHONE");
        return new CommonResponse<>(0, "验证码获取成功 🙆", result);
    }

    @GetMapping("/sms/check")
    @ResponseBody
    public  CommonResponse<Object> checkSmsCode(@Valid PhoneSmsCheckRequest req) {
        String code = req.getCode();
        String phoneNumber = req.getPhone();
        String key = req.getKey();
        boolean isValid = phoneService.checkSmsCode(code, phoneNumber, key);
        int resultCode = isValid ? 0 : 1;
        String message = isValid ? "验证成功" : "验证失败";
        return new CommonResponse<>(resultCode, message, isValid);
    }


    @GetMapping("/sms/login")
    @ResponseBody
    public  CommonResponse<Object> loginByPhone(@Valid PhoneSmsCheckRequest req) {
        String code = req.getCode();
        String phoneNumber = req.getPhone();
        String key = req.getKey();

        boolean isValid = phoneService.checkSmsCode(code, phoneNumber, key);
        if(!isValid) {
            return new CommonResponse<>(1, "验证失败", null);
        }
        // 直接通过手机号查询用户
        User user = userRepository.findByPhone(phoneNumber);

        if (user == null) {
            throw new BusinessException("手机号未注册 🙅");
        }

        // 生成JWT token
        String token = jwt.encode(user.getId(), "web", false);

        // 构建包含用户信息和token的Map
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", UserIndexResponse.fromUser(user));
        responseMap.put("token", token);
        return new CommonResponse<>(responseMap);
    }


}
