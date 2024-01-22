package com.aitschool.user.Rabc.service;

import com.aitschool.common.exception.BusinessException;
import com.aitschool.common.response.PageJPAResponse;
import com.aitschool.user.Rabc.model.Administrator;
import com.aitschool.user.Rabc.model.Role;
import com.aitschool.user.Rabc.response.AdministratorIndexResponse;
import com.aitschool.user.Rabc.request.AdministratorIndexRequest;
import com.aitschool.user.Rabc.repository.AdministratorRepository;
import com.aitschool.user.Rabc.repository.RoleRepository;
import com.aitschool.user.User.model.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private RoleRepository roleRepository;

    // 获取管理员列表
    public PageJPAResponse<AdministratorIndexResponse> index(AdministratorIndexRequest req, Pageable pageRequest) {
        Specification<Administrator> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (req.getUser_id() != null) {
                predicates.add(criteriaBuilder.equal(root.get("userId"), req.getUser_id()));
            }

            if (req.getPhone() != null) {
                Join<Administrator, User> userJoin = root.join("user");
                predicates.add(criteriaBuilder.like(userJoin.get("phone"), "%" + req.getPhone() + "%"));
            }

            if(req.getRole_id() != null) {
                Join<Administrator, Role> roleJoin = root.join("roles", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(roleJoin.get("id"), req.getRole_id()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<AdministratorIndexResponse> administratorPage = administratorRepository.findAll(spec, pageRequest).map(AdministratorIndexResponse::format);
        return PageJPAResponse.of(
                administratorPage.getContent(),
                administratorPage.getTotalElements(),
                administratorPage.getNumberOfElements(),
                administratorPage.getSize(),
                administratorPage.getNumber(),
                administratorPage.getTotalPages()
        );
    }

    // 创建管理员
    public void store(Long userId, Long[] roleIds) {
        Administrator administrator = new Administrator();
        administrator.setUserId(userId);
        List<Role> newRoles = roleRepository.findAllById(List.of(roleIds));
        administrator.getRoles().addAll(newRoles);
        administratorRepository.save(administrator);
    }

    // 更新管理员 roleIds [2,3]
    @Transactional
    public Administrator update(Long administratorId, Long[] roleIds) {
        Administrator administrator = administratorRepository.findById(administratorId);
        if(administrator == null) {
            throw new BusinessException("找不到管理员 🙅");
        }

        // 获取现有角色的ID集合[1,2]
        Set<Long> existingRoleIds = administrator.getRoles().stream()
                .map(role -> role.getId())
                .collect(Collectors.toSet());

        // 删除不再拥有的角色[1]
        Set<Long> rmRoleIds = existingRoleIds.stream()
                .filter(id -> !Arrays.asList(roleIds).contains(id))
                .collect(Collectors.toSet());
        administrator.getRoles().removeIf(role ->
                rmRoleIds.contains(role.getId()));

        // 需要新增的角色
        Set<Long> newRoleIds = Arrays.asList(roleIds).stream()
                .filter(id -> !existingRoleIds.contains(id))
                .collect(Collectors.toSet());
        List<Role> newRoles = roleRepository.findAllById(newRoleIds);

        administrator.getRoles().addAll(newRoles);
        administratorRepository.save(administrator);
        return administrator;
    }

    @Transactional
    public void delete(Long administratorId) {
        Administrator administrator = administratorRepository.findById(administratorId);
        if(administrator == null) {
            throw new BusinessException("找不到管理员 🙅");
        }
        // 删除管理员与角色的关联
        administrator.getRoles().clear();
        // 删除管理员
        administratorRepository.deleteById(administratorId);
    }
}
