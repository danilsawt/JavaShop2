package com.mebel.shop.models;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class UsersRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @NotNull
    private Item item;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOpened;

    private Date dateClosed;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="order_status", joinColumns=@JoinColumn(name="order_id"), inverseJoinColumns=@JoinColumn(name="status_id"))
    private List<Status> updates;

    @ManyToOne
    @CreatedBy
    private User createdBy;

    private Stage stage;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public UsersRequest() {
        this.stage = Stage.OPEN;
    }

    public UsersRequest(Item item, User createdBy) {
        this.item = item;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getOrder_name() {
        return item;
    }

    public void setOrder_name(Item item) {
        this.item = item;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Status> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Status> updates) {
        this.updates = updates;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
