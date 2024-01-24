package com.aitschool.user.Rabc.repository;

import com.aitschool.user.Rabc.model.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {

    List<PermissionGroup> findByNameNotIn(List<String> groupNamesInConstants);

    Optional<PermissionGroup> findByName(String name);
}
