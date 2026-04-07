package com.printing_shop.Config;

import com.printing_shop.Enity.User;
import com.printing_shop.Enity.ProfileEnity;
import com.printing_shop.Repositories.UserRepository;
import com.printing_shop.Repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String email = "admin@printshop.com";
        if (userRepository.findByEmail(email).isEmpty()) {
            // 1. Create User
            User user = User.builder()
                    .fullName("System Admin")
                    .email(email)
                    .password(passwordEncoder.encode("admin123"))
                    .role("ADMIN")
                    .build();
            User savedUser = userRepository.save(user);

            // 2. Create Profile (Crucial step!)
            ProfileEnity adminProfile = ProfileEnity.builder()
                    .userId(savedUser.getId()) // Ensure this is Long or cast to Integer
                    .fullName(savedUser.getFullName())
                    .phone(savedUser.getPhone())
                    .address("Phnom Penh, Cambodia")
                    .avatar("/uploads/default-admin.png")
                    .build();

            profileRepository.save(adminProfile);
            System.out.println("🚀 Admin and Profile seeded for ID: " + savedUser.getId());
        }
    }
}