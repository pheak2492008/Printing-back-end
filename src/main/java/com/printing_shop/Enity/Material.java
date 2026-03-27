package com.printing_shop.Enity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "material")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // This matches the column that has the value '10', '5', etc. in your photo
    @Column(name = "price_per_sqm", nullable = false)
    private Double pricePerM2;

    @Column(columnDefinition = "TEXT")
    private String description;

    // If you need this for consistency
    public Long getMaterialId() {
        return this.id;
    }
}