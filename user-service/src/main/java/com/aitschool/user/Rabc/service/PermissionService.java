package com.aitschool.user.Rabc.service;

import com.aitschool.user.Rabc.constant.PermissionConstants;
import com.aitschool.user.Rabc.model.Permission;
import com.aitschool.user.Rabc.model.PermissionGroup;
import com.aitschool.user.Rabc.model.Role;
import com.aitschool.user.Rabc.repository.PermissionGroupRepository;
import com.aitschool.user.Rabc.repository.PermissionRepository;
import com.aitschool.user.Rabc.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Map<String, Object>> getPermissionsByGroup() {
        return  PermissionConstants.GROUPS;
    }

    @Transactional
    public List<Map<String, Object>> syncDatabaseWithConstants() {
        removeGroupsNotInConstants();

        List<Map<String, Object>> groups = PermissionConstants.GROUPS;
        for (int i = 0; i < groups.size(); i++) {
            Map<String, Object> groupMap = groups.get(i);
            String groupName = (String) groupMap.get("name");
            String groupDisplayName = (String) groupMap.get("display_name");
            List<Map<String, String>> groupPermissions = (List<Map<String, String>>) groupMap.get("permissions");
            Optional<PermissionGroup> permissionGroupOptional = permissionGroupRepository.findByName(groupName);
            PermissionGroup permissionGroup = permissionGroupOptional.orElseGet(() -> {
                PermissionGroup newPermissionGroup = new PermissionGroup();
                newPermissionGroup.setName(groupName);
                newPermissionGroup.setDisplayName(groupDisplayName);
                newPermissionGroup.setCreatedAt(new Date());
                return permissionGroupRepository.save(newPermissionGroup);
            });
            permissionGroup.setDisplayName(groupDisplayName);
            permissionGroup.setSort(i);
            permissionGroup.setUpdatedAt(new Date());

            List<String> permissionNamesInConstants = groupPermissions.stream()
                    .map(groupPermissionMap -> groupPermissionMap.get("name").toString())
                    .collect(Collectors.toList());
            permissionGroup.getPermissions().removeIf(permission ->
                    !permissionNamesInConstants.contains(permission.getName()));

            for (int j = 0; j < groupPermissions.size(); j++) {
                Map<String, String> permissionMap = groupPermissions.get(j);
                String permissionName = (String) permissionMap.get("name");
                String permissionDisplayName = (String) permissionMap.get("display_name");
                Optional<Permission> permissionOptional = permissionRepository.findByName(permissionName);
                if (permissionOptional.isPresent()) {
                    Permission existingPermission = permissionOptional.get();
                    existingPermission.setDisplayName(permissionDisplayName);
                    existingPermission.setUpdatedAt(new Date());
                    existingPermission.setSort(j);
                } else {
                    System.out.print(permissionName);
                    System.out.print(permissionGroup.getName());
                    Permission newPermission = new Permission();
                    newPermission.setName(permissionName);
                    newPermission.setDisplayName(permissionDisplayName);
                    newPermission.setGroup(permissionGroup);
                    newPermission.setSort(j);
                    newPermission.setCreatedAt(new Date());
                    newPermission.setUpdatedAt(new Date());
                    permissionGroup.getPermissions().add(newPermission);
                }
            }
            permissionGroupRepository.save(permissionGroup);
        }

        bindPermissionToSuperAdministratorRole();

        return PermissionConstants.GROUPS;
    }

    private void removeGroupsNotInConstants() {
        List<String> groupNamesInConstants = PermissionConstants.GROUPS.stream()
                .map(groupMap -> groupMap.get("name").toString())
                .collect(Collectors.toList());
        List<PermissionGroup> groupsToDelete = permissionGroupRepository.findByNameNotIn(groupNamesInConstants);
        groupsToDelete.forEach(permissionGroupRepository::delete);
    }

    private void bindPermissionToSuperAdministratorRole() {
        // 检查是否存在名为"超级管理员"的角色，如果不存在，则创建
        Optional<Role> superAdminRoleOptional = roleRepository.findByName("super-administrator");
        Role superAdministratorRole = superAdminRoleOptional.orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("super-administrator");
            newRole.setDisplayName("超级管理员");
            newRole.setDescription("拥有所有权限的管理员");
            newRole.setCreatedAt(new Date());
            return roleRepository.save(newRole);
        });

        List<Permission> allPermissions = permissionRepository.findAll();
        // 检查并添加不存在的权限
        for (Permission permission : allPermissions) {
            if (!superAdministratorRole.getPermissions().contains(permission)) {
                superAdministratorRole.getPermissions().add(permission);
            }
        }
        // 保存角色以更新权限关联
        roleRepository.save(superAdministratorRole);
    }

}
