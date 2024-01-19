package com.aitschool.user.Rabc.controller.admin;

import com.aitschool.common.response.CommonResponse;
import com.aitschool.user.Rabc.request.AdministratorIndexRequest;
import com.aitschool.user.Rabc.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/admin/rabc")
public class AdminRabcController {

    @Autowired
    private AdministratorService administratorService;

    // 管理者
    @GetMapping(path="/administrators")
    @ResponseBody
    public CommonResponse<Object> administratorIndex(AdministratorIndexRequest administratorIndexRequest, Pageable pageRequest) {
        return new CommonResponse<>(administratorService.getAdministratorList(administratorIndexRequest, pageRequest));
    }

    @PostMapping(path="/administrators")
    @ResponseBody
    public CommonResponse<Object> administratorStore() {
        return new CommonResponse<>(null);
    }

    @GetMapping(path="/administrators/{id}")
    @ResponseBody
    public CommonResponse<Object> administratorShow() {
        return new CommonResponse<>(null);
    }

    @PutMapping(path="/administrators/{id}")
    @ResponseBody
    public CommonResponse<Object> administratorUpdate() {
        return new CommonResponse<>(null);
    }


    // 角色
    @GetMapping(path="/roles")
    @ResponseBody
    public CommonResponse<Object> roleIndex(Pageable request) {
        return new CommonResponse<>(null);
    }

    @PostMapping(path="/roles")
    @ResponseBody
    public CommonResponse<Object> roleStore() {
        return new CommonResponse<>(null);
    }

    @PutMapping(path="/roles/{id}")
    @ResponseBody
    public CommonResponse<Object> roleUpdate() {
        return new CommonResponse<>(null);
    }

    @GetMapping(path="/roles/{id}")
    @ResponseBody
    public CommonResponse<Object> roleShow() {
        return new CommonResponse<>(null);
    }

    // 权限
    @GetMapping(path="/permissions")
    @ResponseBody
    public CommonResponse<Object> permissionIndex() {
        return new CommonResponse<>(null);
    }

}
