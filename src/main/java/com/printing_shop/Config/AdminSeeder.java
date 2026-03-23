package com.printing_shop.Config;
import com.printing_shop.Enity.User;
import com.printing_shop.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class AdminSeeder implements CommandLineRunner {
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Define Admin details
        String adminEmail = "admin@printshop.com";

        // 2. Check if Admin already exists to avoid duplicates
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            
            User admin = User.builder()
                    .fullName("System Administrator")
                    .email(adminEmail)
                    .phone("012345678")
                    .password(passwordEncoder.encode("admin123")) // Default password
                    .role("ADMIN") // Role for Dashboard access
                    .build();

            userRepository.save(admin);
            
            System.out.println("\n" + "=".repeat(40));
            System.out.println("🚀 ADMIN ACCOUNT SEEDED SUCCESSFULLY");
            System.out.println("📧 Email: " + adminEmail);
            System.out.println("🔑 Password: admin123");
            System.out.println("=".repeat(40) + "\n");
        } else {
            System.out.println("ℹ️ Admin account already exists. Skipping seed...");
        }
    }

}
