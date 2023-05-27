package com.mebel.shop.models;

import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long articul;

    @NotNull
    private String name;

    @NotNull
    private String ed_izmer;

    @NotNull
    private double price;

    @NotNull
    private byte[] photo_name;

    public Item() {
    }

    public Item(Long id, Long articul, String name, String ed_izmer, double price) {
        this.id = id;
        this.articul = articul;
        this.name = name;
        this.ed_izmer = ed_izmer;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticul() {
        return articul;
    }

    public void setArticul(Long articul) {
        this.articul = articul;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEd_izmer() {
        return ed_izmer;
    }

    public void setEd_izmer(String ed_izmer) {
        this.ed_izmer = ed_izmer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getPhoto_name() {
        return photo_name;
    }

    public void setPhoto_name(byte[] photo_name) {
        this.photo_name = photo_name;
    }
}
