package com.aitschool.user.Rabc.request;

import lombok.Data;

@Data
public class AdministratorIndexRequest {
    private String phone;
    private Long user_id;
    private Long role_id;
}
