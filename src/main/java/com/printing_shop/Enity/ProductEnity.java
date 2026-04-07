package com.printing_shop.Enity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    

    // Matches your ERD's requirement for a specific Product ID
    private Integer productId;

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getPrice() {
		// TODO Auto-generated method stub
		return null;
	} 
}