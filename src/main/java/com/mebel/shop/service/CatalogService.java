package com.mebel.shop.service;

import com.mebel.shop.models.Item;
import com.mebel.shop.models.Stage;
import com.mebel.shop.models.User;
import com.mebel.shop.models.UsersRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class CatalogService {
    @PersistenceContext
    private EntityManager em;

    public Item getItemById(Long itemArt) {
        return em.createQuery("select item from Item item where item.articul=:param", Item.class)
                .setParameter("param", itemArt).getSingleResult();
    }

    public List<UsersRequest> getAllReq() {
        return em.createQuery("select orders from UsersRequest orders", UsersRequest.class)
                .getResultList();
    }

    public List<UsersRequest> getAllUsersReq(User user) {
        return em.createQuery("select orders from UsersRequest orders where orders.createdBy=:param", UsersRequest.class)
                .setParameter("param", user).getResultList();
    }

    public List<UsersRequest> getAllReqInOpen(Stage first, Stage second) {
        return em.createQuery("select orders from UsersRequest orders where orders.stage=:param1 or orders.stage=:param2", UsersRequest.class)
                .setParameter("param1", first).setParameter("param2", second).getResultList();
    }

    public UsersRequest getReqById(Long id){
        return em.createQuery("select orders from UsersRequest orders where orders.id=:param", UsersRequest.class)
                .setParameter("param", id).getSingleResult();
    }

    public Item getItemId(Long id) {
        return em.createQuery("select orders from UsersRequest orders where orders.id=:param", Item.class)
                .setParameter("param", id).getSingleResult();
    }

    public List<Item> getAllItems() {
        return em.createQuery("select item from Item item", Item.class)
                .getResultList();
    }

    public boolean isUsersTicket(User user, Long ticketId) {
        try {
            em.createQuery("select orders from UsersRequest orders where orders.createdBy=:param1 and orders.id=:param2", UsersRequest.class)
                    .setParameter("param1", user).setParameter("param2", ticketId).getSingleResult();
            return true;
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
