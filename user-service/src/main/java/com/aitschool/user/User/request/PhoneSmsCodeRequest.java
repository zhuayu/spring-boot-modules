package com.aitschool.user.User.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PhoneSmsCodeRequest {
    @NotBlank(message = "手机号不能为空  🙅")
    @Pattern(regexp = "^1[3-9][0-9]{9}$", message = "手机号格式不正确 🙅")
    private String phone;
}
