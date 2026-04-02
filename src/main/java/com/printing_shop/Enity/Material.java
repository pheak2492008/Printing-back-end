package com.printing_shop.Enity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "material")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // hides ID from incoming JSON
    private Long materialId; 
    
    @Column(nullable = false)
    private String name;

    @Column(name = "price_per_sqm", nullable = false)
    private Double pricePerM2;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Convenience method for other code that expects getId()
    public Long getId() {
        return materialId;
    }
}