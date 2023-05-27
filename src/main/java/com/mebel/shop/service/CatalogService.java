package com.mebel.shop.service;

import com.mebel.shop.models.Item;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class CatalogService {
    @PersistenceContext
    private EntityManager em;

    public List<Item> getAllItems() {
        return em.createQuery("select item from Item item", Item.class)
                .getResultList();
    }
}
