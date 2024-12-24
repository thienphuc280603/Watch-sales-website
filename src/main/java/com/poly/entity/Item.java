package com.poly.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.NonFinal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "ordersid", referencedColumnName = "ordersid")
    private Order order;   
    @ManyToOne
    @JoinColumn(name = "productsid", referencedColumnName = "productsid")
    private Products product;   
    @Column(name = "quantity")
    private int quantity;    
    @Column(name = "price")
    private float price;
}

