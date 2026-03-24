package com.printing_shop.Enity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    
    private Double width;
    private Double length;
    private Double totalPrice;
    private String status;
    private String dpiQuality;
    private String inkChoice;
    private Boolean hasGrommets;
    private Boolean hasHems;
    private String designFileUrl; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false) // Fixes the null mapping
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "material_id", nullable = false) // Fixes the null mapping
    private Material material;
}