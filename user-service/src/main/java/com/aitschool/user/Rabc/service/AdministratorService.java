package com.aitschool.user.Rabc.service;

import com.aitschool.common.response.PageJPAResponse;
import com.aitschool.user.Rabc.model.Administrator;
import com.aitschool.user.Rabc.model.Role;
import com.aitschool.user.Rabc.model.RoleAdministrator;
import com.aitschool.user.Rabc.repository.AdministratorIndexResponse;
import com.aitschool.user.Rabc.request.AdministratorIndexRequest;
import com.aitschool.user.Rabc.respository.AdministratorRepository;
import com.aitschool.user.User.model.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    // 获取管理员列表
    public PageJPAResponse getAdministratorList(AdministratorIndexRequest req, Pageable pageRequest) {
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
                Join<Administrator, RoleAdministrator> roleJoin = root.join("roles", JoinType.LEFT);
                Join<RoleAdministrator, Role> roleTableJoin = roleJoin.join("role", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(roleTableJoin.get("id"), req.getRole_id()));
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
}
