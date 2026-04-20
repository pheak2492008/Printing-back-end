package com.printing_shop.Enity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    // Added to link to your ProductDetail (e.g., "Banner", "Sticker")
    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    private Double width;
    private Double length;
    private Double pricePerM2; 
    private Double subtotal;   

    private Integer quantity;
}