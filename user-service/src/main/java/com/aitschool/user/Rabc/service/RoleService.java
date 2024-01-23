package com.aitschool.user.Rabc.service;

import com.aitschool.common.exception.BusinessException;
import com.aitschool.common.response.PageJPAResponse;
import com.aitschool.user.Rabc.model.Permission;
import com.aitschool.user.Rabc.model.Role;
import com.aitschool.user.Rabc.repository.PermissionRepository;
import com.aitschool.user.Rabc.repository.RoleRepository;
import com.aitschool.user.Rabc.request.RoleIndexRequest;
import com.aitschool.user.Rabc.request.RoleStoreRequest;
import com.aitschool.user.Rabc.response.RoleIndexResponse;
import com.aitschool.user.Rabc.response.RoleShowResponse;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public PageJPAResponse<RoleIndexResponse> index(RoleIndexRequest req, Pageable pageRequest) {
        Specification<Role> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (req.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), req.getId()));
            }
            if (req.getName() != null) {
                Predicate nameLike = criteriaBuilder.like(root.get("name"), "%" + req.getName() + "%");
                Predicate displayNameLike = criteriaBuilder.like(root.get("displayName"), "%" + req.getName() + "%");
                predicates.add(criteriaBuilder.or(nameLike, displayNameLike));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<RoleIndexResponse> rolePage = roleRepository.findAll(spec, pageRequest).map(RoleIndexResponse::format);
        return PageJPAResponse.of(
                rolePage.getContent(),
                rolePage.getTotalElements(),
                rolePage.getNumberOfElements(),
                rolePage.getSize(),
                rolePage.getNumber(),
                rolePage.getTotalPages()
        );
    }

    public RoleShowResponse show(Long id) {
        Role role = roleRepository.findAllById(id);
        return RoleShowResponse.format(role);
    }

    public RoleShowResponse store(RoleStoreRequest req) {
        Role role = new Role();
        role.setName(req.getName());
        role.setDisplayName(req.getDisplay_name());
        role.setDescription(req.getDescription());
        role.setCreatedAt(new Date());
        List<Long> permissionIds = List.of(req.getPermission_ids());
        List<Permission> newPermissions = permissionRepository.findAllById(permissionIds);
        role.getPermissions().addAll(newPermissions);
        return RoleShowResponse.format(roleRepository.save(role));
    }

    @Transactional
    public RoleShowResponse update(Long id, RoleStoreRequest req) {
        Role role = roleRepository.findAllById(id);
        if(role == null) {
            throw new BusinessException("找不到角色 🙅");
        }
        role.setName(req.getName());
        role.setDisplayName(req.getDisplay_name());
        role.setDescription(req.getDescription());
        role.setUpdatedAt(new Date());
        List<Long> permissionIds = List.of(req.getPermission_ids());
        // 获取现有集合
        Set<Long> existingIds = role.getPermissions().stream()
                .map(item -> item.getId())
                .collect(Collectors.toSet());

        // 删除没有的集合
        Set<Long> removeIds = existingIds.stream()
                .filter(removeId -> !permissionIds.contains(removeId))
                .collect(Collectors.toSet());
        role.getPermissions().removeIf(item ->
                removeIds.contains(item.getId()));

        // 需要新增的集合
        Set<Long> insertIds = permissionIds.stream()
                .filter(permissionId -> !existingIds.contains(permissionId))
                .collect(Collectors.toSet());
        List<Permission> newPermissions = permissionRepository.findAllById(insertIds);
        role.getPermissions().addAll(newPermissions);
        return RoleShowResponse.format(roleRepository.save(role));
    }

    @Transactional
    public void delete(Long id) {
        Role role = roleRepository.findAllById(id);
        if(role == null) {
            throw new BusinessException("找不到角色 🙅");
        }
        role.getPermissions().clear();
        roleRepository.deleteById(id);
    }

}
