package com.printing_shop.Enity;   // Fixed spelling: Enity → Entity

import jakarta.persistence.*;
import com.printing_shop.Enity.Material;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status = "PENDING";

    private LocalDateTime createdAt = LocalDateTime.now();
}