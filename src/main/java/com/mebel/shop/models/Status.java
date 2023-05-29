package com.mebel.shop.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "status")
public class Status implements Comparable<Status>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="status_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @NotNull
    private Item item;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Status() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }




    @Override
    public int compareTo(Status status) {
        if (getUpdateDate() == null || status.getUpdateDate() == null){
            return 0;
        }
        return getUpdateDate().compareTo(status.getUpdateDate());
    }
}
