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
	    private String tag;
	    private String tagColor;
	    private String bgColor;
	    private String iconBg;

}
