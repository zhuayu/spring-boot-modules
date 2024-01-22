package com.aitschool.user.Rabc.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AdministratorUpdateRequest {
    @NotEmpty(message = "角色ID不能为空  🙅")
    private Long[] role_ids;
}
