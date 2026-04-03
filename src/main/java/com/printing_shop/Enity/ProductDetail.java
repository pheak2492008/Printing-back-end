package com.printing_shop.Enity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class ProductDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String specifications;
    private String materialList; // e.g., "Standard PVC, Mesh, Fabric"

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEnity product;
}