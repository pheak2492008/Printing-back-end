package com.printing_shop.Enity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String name;
    private Double price;

    @Column(length = 1000)
    private String description;

    private Integer productId;
    private Integer stock;
    private String imageUrl;        // Will store full Cloudinary URL
}