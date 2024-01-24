package com.aitschool.user.Rabc.repository;

import com.aitschool.user.Rabc.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);


    List<Permission> findByNameNotInAndGroupId(List<String> names, Long id);
}
