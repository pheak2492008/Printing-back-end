package com.printing_shop.dtoRequest;

import lombok.Data;

@Data
public class ProductRequest {
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer productId;
	public Integer getStock() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}