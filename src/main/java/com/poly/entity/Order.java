package com.poly.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ordersid")
	    private int orderId;   
	    @Column(name = "date")
	    private Date date;  
	    @ManyToOne
	    @JoinColumn(name = "userid", referencedColumnName = "userid")
	    private User user;   
	    @Column(name = "address")
	    private String address;
	    @Column(name = "phone")
	    private String phone;
	    @Column(name = "price")
	    private float price;
	    @Column(name = "payment")
	    private String payment;
	    @OneToMany(mappedBy = "order")
	    private Set<Item> items;
	    @Column(name = "active")
	    private boolean active;
	    @Column(name ="status")
	    private boolean status;
}
