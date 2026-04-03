package com.printing_shop.Enity;

import jakarta.persistence.*;
import com.printing_shop.Enity.Material;
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

    private Double width;
    private Double length;
    private Double pricePerM2;
    private Double subtotal;

    // ⚡ Remove this! Lombok handles it
    // public static OrderItemBuilder builder() { return null; }
}