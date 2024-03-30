package com.example.security_last_try.repository;


import com.example.security_last_try.entity.Role;
import com.example.security_last_try.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String  str);
    User findByRole(Role role);

}
