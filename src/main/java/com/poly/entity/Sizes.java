package com.poly.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sizes")
public class Sizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sizeid")
    private int sizeId;    
    @Column(name = "size", length = 20)
    private String size; 
    @ManyToOne
    @JoinColumn(name = "categorizesid", referencedColumnName = "categorizesid")
    private Categorizes categorizes;   
    @ManyToOne
    @JoinColumn(name = "productsid", referencedColumnName = "productsid")
    private Products product;
}
