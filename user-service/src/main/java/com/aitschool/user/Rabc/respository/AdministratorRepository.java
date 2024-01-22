package com.aitschool.user.Rabc.respository;

import com.aitschool.user.Rabc.model.Administrator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdministratorRepository extends PagingAndSortingRepository<Administrator, Long>, JpaSpecificationExecutor<Administrator> {

    Page<Administrator> findAll(Specification<Administrator> spec, Pageable pageable);

    boolean existsByUserId(Long userId);

    void deleteById(Long id);

    void save(Administrator administrator);

    Administrator findById(Long administratorId);

}

