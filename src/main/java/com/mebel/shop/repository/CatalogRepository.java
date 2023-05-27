package com.mebel.shop.repository;

import com.mebel.shop.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Item, Long> {
}
