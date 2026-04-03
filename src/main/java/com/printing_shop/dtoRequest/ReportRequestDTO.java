package com.printing_shop.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId; // Changed from Integer to Long

    @NotNull(message = "Order ID is required")
    private Long orderId; // Changed from Integer to Long

    @NotBlank(message = "Subject cannot be empty")
    private String subject;

    @NotBlank(message = "Description cannot be empty")
    private String description;
}