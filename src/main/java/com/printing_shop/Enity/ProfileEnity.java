package com.printing_shop.Enity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileEnity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private String phone;
    private String address;
    private String avatar;

    @Column(name = "admin_user_id", unique = true)
    private Long userId; // Change this from Integer to Long
}