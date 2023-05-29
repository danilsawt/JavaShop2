package com.mebel.shop.repository;

import com.mebel.shop.models.Item;
import com.mebel.shop.models.UsersRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<UsersRequest, Long> {
}
