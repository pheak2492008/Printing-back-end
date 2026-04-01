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
    @Column(name = "order_id") // Maps the field to the DB column
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
}