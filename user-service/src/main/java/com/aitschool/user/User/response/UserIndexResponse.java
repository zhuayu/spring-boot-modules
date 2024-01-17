package com.aitschool.user.User.response;

import com.aitschool.user.User.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class UserIndexResponse {

    private long id;
    private String email;
    private String phone;
    private String nickname;
    private String avatarUrl;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date created_at;

    // 构造函数或静态工厂方法
    public static UserIndexResponse fromUser(User user) {
        UserIndexResponse response = new UserIndexResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setNickname(user.getNickname());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setBirthday(user.getBirthday());
        response.setCreated_at(user.getCreatedAt());
        return response;
    }

}
