package com.aitschool.user.Rabc.response;

import com.aitschool.user.Rabc.model.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoleShowResponse {
    private long id;
    private String name;
    private String displayName;
    private List permission_ids;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date created_at;

    public static RoleShowResponse format(Role role) {
        RoleShowResponse response = new RoleShowResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setDisplayName(role.getDisplayName());
        response.setCreated_at(role.getCreatedAt());
        response.setPermission_ids(role.getPermissions().stream().map(permission -> permission.getId()).collect(Collectors.toList()));
        return response;
    }
}
