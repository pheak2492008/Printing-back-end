package com.printing_shop.Enity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    private String role; // "CUSTOMER" or "ADMIN"
    
    private String staffId;
}