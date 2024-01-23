package com.aitschool.user.Rabc.repository;

import com.aitschool.user.Rabc.model.Administrator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AdministratorRepository extends PagingAndSortingRepository<Administrator, Long>, JpaSpecificationExecutor<Administrator> {

    Page<Administrator> findAll(Specification<Administrator> spec, Pageable pageable);

    boolean existsByUserId(Long userId);

    void deleteById(Long id);

    Administrator save(Administrator administrator);

    Administrator findById(Long administratorId);

    Optional<Administrator> findByUserIdAndRolesId(Long userId, Long id);

    Optional<Administrator> findByUserId(Long userId);
}

