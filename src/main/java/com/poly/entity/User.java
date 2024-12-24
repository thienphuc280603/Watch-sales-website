package com.poly.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"discountsUser"})
public class User {
	@Id
    @Column(name = "userid", length = 50)
    private String userId; 
    @Column(name = "fullname", length = 50)
    private String fullName;   
    @Column(name = "password", length = 50)
    private String password;  
    @Column(name = "gender")
    private boolean gender;   
    @Column(name = "email", length = 50)
    private String email;  
    @Column(name = "address", length = 100)
    private String address;  
    @Column(name = "phone", length = 20)
    private String phone;   
    @Column(name = "active")
    private boolean active;
    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "user")
    private Set<DiscountUser> discountsUser;   
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Order> orders;
       
    public User(String userId) {
        this.userId = userId;
    }
    
    
}
