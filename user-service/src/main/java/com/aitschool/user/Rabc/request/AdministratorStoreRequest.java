package com.aitschool.user.Rabc.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AdministratorStoreRequest {
    @NotBlank(message = "手机号不能为空  🙅")
    @Pattern(regexp = "^1[3-9][0-9]{9}$", message = "手机号格式不正确 🙅")
    private String phone;
    @NotEmpty(message = "角色ID不能为空  🙅")
    private Long[] role_ids;
}
