package com.ead.authuser.services;

import com.ead.authuser.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();

    Page<UserModel> findAll(Pageable pageable);

    Page<UserModel> findAll(Specification specification, Pageable pageable);

    Optional<UserModel> findById(UUID userId);

    void delete(UUID userId);

    void save(UserModel userModel);

    boolean existByUsername(String username);

    boolean existByEmail(String email);
}
