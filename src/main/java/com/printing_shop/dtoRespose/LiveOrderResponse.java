package com.printing_shop.dtoRespose;

import lombok.Data;

@Data
public class LiveOrderResponse {
    private String orderNumber;   // e.g. "#100"
    private String clientName;    // e.g. "Phallia"
    private String specs;         // e.g. "2m x 5m"
    private String priority;      // "High", "Low", "Normal"
    private String fileName;
    private String status;        // "Pending", "Printing", "Done"
}