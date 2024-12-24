package com.poly.entity;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discountsuser")
public class DiscountUser {
    @Id
    @Column(name = "id")
    private String id;   
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User user;  
    @Column(name = "discountamount", precision = 10, scale = 2)
    private double discountAmount;
    
    @Transient
    public String getUserId() {
        return user != null ? user.getUserId() : null;
    }
}

