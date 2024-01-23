package com.aitschool.user.Rabc.controller.admin;

import com.aitschool.common.exception.BusinessException;
import com.aitschool.common.response.CommonResponse;
import com.aitschool.user.Rabc.request.*;
import com.aitschool.user.Rabc.repository.AdministratorRepository;
import com.aitschool.user.Rabc.service.AdministratorService;
import com.aitschool.user.Rabc.service.RoleService;
import com.aitschool.user.User.model.User;
import com.aitschool.user.User.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/admin/rabc")
public class AdminRabcController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private RoleService roleService;

    // 管理者列表
    @GetMapping(path="/administrators")
    @ResponseBody
    public CommonResponse<Object> index(AdministratorIndexRequest administratorIndexRequest, Pageable pageRequest) {
        return new CommonResponse<>(administratorService.index(administratorIndexRequest, pageRequest));
    }

    // 新建管理者
    @PostMapping(path="/administrators")
    @ResponseBody
    public CommonResponse<Object> store(@RequestBody @Valid AdministratorStoreRequest req) {
        // 检查是否注册过
        User user = userRepository.findByPhone(req.getPhone());
        if (user == null) {
            throw new BusinessException("手机号未注册了 ！ 🙅");
        }
        Long userId = user.getId();
        Long[] roleIds = req.getRole_ids();
        if (administratorRepository.existsByUserId(userId)) {
            throw new BusinessException("用户已经是管理员了！ 🙅");
        }
        // 新增用户
        administratorService.store(userId, roleIds);
        return new CommonResponse<>(userId);
    }

    // 修改管理者
    @PutMapping(path="/administrators/{id}")
    @ResponseBody
    public CommonResponse<Object> update(@PathVariable Long id, @RequestBody @Valid AdministratorUpdateRequest req) {
        Long[] roleIds = req.getRole_ids();
        return new CommonResponse<>(administratorService.update(id, roleIds));
    }

    // 删除管理者
    @DeleteMapping(path = "/administrators/{id}")
    @ResponseBody
    public CommonResponse<Object> delete(@PathVariable Long id) {
        administratorService.delete(id);
        return new CommonResponse<>();
    }


    // 角色
    @GetMapping(path="/roles")
    @ResponseBody
    public CommonResponse<Object> roleIndex(RoleIndexRequest req, Pageable pageRequest) {
        return new CommonResponse<>(roleService.index(req, pageRequest));
    }

    @PostMapping(path="/roles")
    @ResponseBody
    public CommonResponse<Object> roleStore(@RequestBody @Valid RoleStoreRequest req) {
        return new CommonResponse<>(roleService.store(req));
    }

    @GetMapping(path="/roles/{id}")
    @ResponseBody
    public CommonResponse<Object> roleShow(@PathVariable Long id) {
        return new CommonResponse<>(roleService.show(id));
    }

    @PutMapping(path="/roles/{id}")
    @ResponseBody
    public CommonResponse<Object> roleUpdate(@PathVariable Long id, @RequestBody @Valid RoleStoreRequest req) {
        return new CommonResponse<>(roleService.update(id, req));
    }

    @DeleteMapping(path="/roles/{id}")
    @ResponseBody
    public CommonResponse<Object> roleUpdate(@PathVariable Long id) {
        roleService.delete(id);
        return new CommonResponse<>(null);
    }

    // 权限
    @GetMapping(path="/permissions")
    @ResponseBody
    public CommonResponse<Object> permissionIndex() {
        return new CommonResponse<>(null);
    }

}
