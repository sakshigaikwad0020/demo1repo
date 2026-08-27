package com.rangoli.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rangoli.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByResetToken(String resetToken);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // Story 11
    List<User> findByFnameContainingIgnoreCase(String fname);

    List<User> findByLnameContainingIgnoreCase(String lname);

    List<User> findByUsernameContainingIgnoreCase(String username);

    List<User> findByEmailContainingIgnoreCase(String email);

    List<User> findByMobileContaining(String mobile);

    List<User> findByStatusIgnoreCase(String status);
    //List<User> searchUsers(String keyword);

}