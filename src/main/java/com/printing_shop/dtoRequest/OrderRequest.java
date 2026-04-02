package com.printing_shop.dtoRequest;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String fullName;
    private String phoneNumber;
    private Double width;
    private Double length;
    private Long materialId;
    private String inkChoice;
    private String dpiQuality;
    private Boolean hasGrommets;
    private Boolean hasHems;
}