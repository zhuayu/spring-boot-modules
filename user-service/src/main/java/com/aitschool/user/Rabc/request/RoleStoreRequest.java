package com.aitschool.user.Rabc.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoleStoreRequest {
    @NotBlank(message = "名称不能为空  🙅")
    private String name;

    @NotBlank(message = "名称中文不能为空  🙅")
    private String display_name;

    @NotBlank(message = "描述不能为空  🙅")
    private String description;

    @NotEmpty(message = "权限ID不能为空  🙅")
    private Long[] permission_ids;
}
