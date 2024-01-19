package com.aitschool.user.Rabc.repository;

import com.aitschool.user.Rabc.model.Administrator;
import com.aitschool.user.Rabc.model.Role;
import com.aitschool.user.Rabc.model.RoleAdministrator;
import com.aitschool.user.User.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AdministratorIndexResponse {
    private long id;
    private String phone;
    private String nickname;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private List<RoleResponse> roles;
    private Date created_at;

    // 构造函数或静态工厂方法
    public static AdministratorIndexResponse format(Administrator administrator) {
        AdministratorIndexResponse response = new AdministratorIndexResponse();
        User user = administrator.getUser();
        response.setId(user.getId());
        response.setPhone(user.getPhone());
        response.setNickname(user.getNickname());
        response.setCreated_at(administrator.getCreatedAt());

        List<RoleResponse> roleResponses = new ArrayList<>();
        for (RoleAdministrator roleAdministrator : administrator.getRoles()) {
            Role role = roleAdministrator.getRole();
            RoleResponse roleResponse = new RoleResponse(role.getId(), role.getName());
            roleResponses.add(roleResponse);
        }
        response.setRoles(roleResponses);
        return response;
    }

    @Data
    public static class RoleResponse {
        private long id;
        private String name;

        public RoleResponse(long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
