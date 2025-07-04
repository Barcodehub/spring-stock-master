package com.barcodehub.eccomerce_spring.repository;

import com.barcodehub.eccomerce_spring.models.Role;
import com.barcodehub.eccomerce_spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findByRole(Role role);

}