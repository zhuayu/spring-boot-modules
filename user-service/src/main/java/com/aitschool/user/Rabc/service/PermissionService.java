package com.aitschool.user.Rabc.service;

import com.aitschool.user.Rabc.constant.PermissionConstants;
import com.aitschool.user.Rabc.model.Permission;
import com.aitschool.user.Rabc.model.PermissionGroup;
import com.aitschool.user.Rabc.repository.PermissionGroupRepository;
import com.aitschool.user.Rabc.repository.PermissionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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


    public void getPermissionsByGroup() {
    
    }
    @Transactional
    public void syncDatabaseWithConstants() {
        for (Map<String, Object> groupMap : PermissionConstants.GROUPS) {
            String groupName = (String) groupMap.get("name");
            String groupDisplayName = (String) groupMap.get("display_name");
            List<Map<String, String>> permissions = (List<Map<String, String>>) groupMap.get("permissions");
            Optional<PermissionGroup> existingGroupOptional = findPermissionGroupByName(groupName);
            if (existingGroupOptional.isPresent()) {
                updateGroup(existingGroupOptional.get(), groupDisplayName, permissions);
            } else {
                insertGroup(groupName, groupDisplayName, permissions);
            }
        }

        removeGroupsNotInConstants();
    }

    public void updateGroup(PermissionGroup group, String displayName, List<Map<String, String>> permissions) {
        group.setDisplayName(displayName);
        group.setUpdatedAt(new Date());

        group.getPermissions().removeIf(permission ->
                permissions.stream().noneMatch(p -> p.get(permission.getName()) != null));

        permissions.forEach(permissionMap -> {
            String permissionName = permissionMap.keySet().iterator().next();
            String permissionDisplayName = permissionMap.get(permissionName);

            Optional<Permission> existingPermissionOptional = findPermissionByNameAndGroupId(permissionName, group.getId());

            if (existingPermissionOptional.isPresent()) {
                Permission existingPermission = existingPermissionOptional.get();
                existingPermission.setDisplayName(permissionDisplayName);
                existingPermission.setUpdatedAt(new Date());
            } else {
                Permission newPermission = new Permission();
                newPermission.setName(permissionName);
                newPermission.setDisplayName(permissionDisplayName);
                newPermission.setGroup(group);
                newPermission.setCreatedAt(new Date());
                newPermission.setUpdatedAt(new Date());
                group.getPermissions().add(newPermission);
            }
        });

        permissionGroupRepository.save(group);
    }
    public void insertGroup(String name, String displayName, List<Map<String, String>> permissions) {
        PermissionGroup newGroup = new PermissionGroup();
        newGroup.setName(name);
        newGroup.setDisplayName(displayName);
        newGroup.setCreatedAt(new Date());
        newGroup.setUpdatedAt(new Date());

        permissions.forEach(permissionMap -> {
            String permissionName = permissionMap.keySet().iterator().next();
            String permissionDisplayName = permissionMap.get(permissionName);

            Permission newPermission = new Permission();
            newPermission.setName(permissionName);
            newPermission.setDisplayName(permissionDisplayName);
            newPermission.setGroup(newGroup);
            newPermission.setCreatedAt(new Date());
            newPermission.setUpdatedAt(new Date());
            newGroup.getPermissions().add(newPermission);
        });

        permissionGroupRepository.save(newGroup);
    }
    private Optional<Permission> findPermissionByNameAndGroupId(String name, Long groupId) {
        return Optional.ofNullable(entityManager.find(Permission.class, new Permission.PrimaryKey(name, groupId)));
    }


    private Optional<PermissionGroup> findPermissionGroupByName(String name) {
        return Optional.ofNullable(entityManager.find(PermissionGroup.class, name));
    }

    public void removeGroupsNotInConstants() {
        List<String> groupNamesInConstants = PermissionConstants.GROUPS.stream()
                .map(groupMap -> groupMap.get("name").toString())
                .collect(Collectors.toList());
        List<PermissionGroup> groupsToDelete = permissionGroupRepository.findByNameNotIn(groupNamesInConstants);
        groupsToDelete.forEach(permissionGroupRepository::delete);
    }

}
