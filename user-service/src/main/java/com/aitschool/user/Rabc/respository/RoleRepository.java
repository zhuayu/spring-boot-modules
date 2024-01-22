package com.aitschool.user.Rabc.respository;

import com.aitschool.user.Rabc.model.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RoleRepository  extends PagingAndSortingRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    List<Role> findAllById(Iterable<Long> ids);
}
