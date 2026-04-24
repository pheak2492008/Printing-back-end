package com.printing_shop.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDTO {

    private Integer reportId;

    // User Context
    private String customerName;
    private String customerEmail;

    // Order Context
    private Long orderId;
    private Double orderTotalPrice;

    // Report Details
    private String subject;
    private String description;
    private String status;
    private LocalDateTime createdAt;
}