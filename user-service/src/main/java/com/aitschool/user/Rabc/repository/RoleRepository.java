package com.aitschool.user.Rabc.repository;

import com.aitschool.user.Rabc.model.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository  extends PagingAndSortingRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    List<Role> findAllById(Iterable<Long> ids);

    <T> Optional<T> findByName(String str);

    Role save(Role newRole);
}
