package com.aitschool.user.Rabc.controller.admin;

import com.aitschool.common.response.CommonResponse;
import com.aitschool.user.Rabc.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/admin/rabc")
public class AdminPermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping(path="/permissions")
    @ResponseBody
    public CommonResponse<Object> permissionIndex() {
        return new CommonResponse<>(permissionService.getPermissionsByGroup());
    }
}
