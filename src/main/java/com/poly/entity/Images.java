package com.poly.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imagesid")
    private int imagesId;    
    @ManyToOne
    @JoinColumn(name = "colorid", referencedColumnName = "colorid")
    private Colors color;  
    @Column(name = "name", length = 100)
    private String name;   
    @ManyToOne
    @JoinColumn(name = "productsid", referencedColumnName = "productsid")
    private Products products;
}
