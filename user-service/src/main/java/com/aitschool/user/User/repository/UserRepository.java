package com.aitschool.user.User.repository;

import com.aitschool.user.User.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhone(String phone);
    User findByPhone(String phoneNumber);
}
