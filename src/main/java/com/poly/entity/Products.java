package com.poly.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productsid")
    private int productsId;
    @Column(name = "name", length = 50)
    private String name;  
    @Column(name = "price")
    private float price;    
    @ManyToOne
    @JoinColumn(name = "categorizesid", referencedColumnName = "categorizesid")
    private Categorizes categorizes;   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discountsproductsid", referencedColumnName = "discountsproductsid")
    private DiscountsProducts discountsProducts;    
    @ManyToOne
    @JoinColumn(name = "brandid", referencedColumnName = "brandid")
    private Brands brand;   
    @Column(name = "describe", length = 300)
    private String describe;  
    @ManyToOne
    @JoinColumn(name = "genderid", referencedColumnName = "genderid")
    private Genders gender;   
    @Column(name = "active")
    private boolean active;
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<Sizes> sizes;   
    @OneToMany(mappedBy = "products")
    private List<Images> images;   
    @OneToMany(mappedBy = "product")
    private Set<Item> items;
    @Column(name="quantity")
    int quantity;
    @Column(name="image")
    String image;
}

