package com.aitschool.user.Rabc.controller.admin;

import com.aitschool.common.response.CommonResponse;
import com.aitschool.user.Rabc.request.RoleIndexRequest;
import com.aitschool.user.Rabc.request.RoleStoreRequest;
import com.aitschool.user.Rabc.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/admin/rabc")
public class AdminRoleController {

    @Autowired
    private RoleService roleService;

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
}
