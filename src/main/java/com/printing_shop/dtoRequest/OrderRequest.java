package com.printing_shop.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Double width;
    private Double length;
    private Long materialId;
    private String inkChoice;
    private String dpiQuality;
    private Boolean hasGrommets;
    private Boolean hasHems;
}