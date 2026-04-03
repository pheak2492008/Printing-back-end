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
    @Column(name = "order_id")
    private Long orderId; 

    private String customerName; 
    private String phoneNumber;

    private Double width;
    private Double length;
    private Double totalPrice;
    private String status; 
    private String dpiQuality;
    private String inkChoice;
    private Boolean hasGrommets;
    private Boolean hasHems;
    private String designFileUrl; 
    
    @Column(length = 1000) 
    private String description;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
}