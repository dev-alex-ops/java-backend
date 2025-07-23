package com.devcodeops.java_backend.repositories;

import com.devcodeops.java_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByMail(String mail);
}
