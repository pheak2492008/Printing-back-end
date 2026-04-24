package com.printing_shop.Enity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter @Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer productId;
}