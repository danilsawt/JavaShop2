package com.mebel.shop.repository;

import com.mebel.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String username);
    User findByEmail(String email);
}
