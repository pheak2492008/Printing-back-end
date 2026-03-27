package com.printing_shop.Enity;

import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class ProfileEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private String phone;
    private String address;
    private String avatar;

    // Relationship with User (assuming you already have User entity)
    @Column(name = "user_id", unique = true)
    private Integer userId;

    // Constructors
    public ProfileEnity() {}

    public ProfileEnity(Integer id, String fullName, String phone, String address, String avatar, Integer userId) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.userId = userId;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
}