package com.aitschool.user.Rabc.response;

import com.aitschool.user.Rabc.model.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RoleIndexResponse {
    private long id;
    private String name;
    private String displayName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date created_at;

    public static RoleIndexResponse format(Role role) {
        RoleIndexResponse response = new RoleIndexResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setDisplayName(role.getDisplayName());
        response.setCreated_at(role.getCreatedAt());
        return response;
    }
}
