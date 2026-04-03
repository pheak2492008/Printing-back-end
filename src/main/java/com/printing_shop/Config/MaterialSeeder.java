package com.printing_shop.Config;

import com.printing_shop.Enity.Material;
import com.printing_shop.Repositories.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MaterialSeeder implements CommandLineRunner {

    private final MaterialRepository materialRepository;

    @Override
    public void run(String... args) {
        if (materialRepository.count() == 0) {
            materialRepository.saveAll(List.of(
                // Banner Section
                Material.builder().name("Banner Standard").pricePerM2(2.01).build(),
                Material.builder().name("Banner UV").pricePerM2(3.01).build(),
                
                // Sticker Section
                Material.builder().name("Sticker China").pricePerM2(2.68).build(),
                Material.builder().name("Sticker Japan").pricePerM2(3.18).build(),
                Material.builder().name("Sticker UV").pricePerM2(3.68).build(),
                
                // Sticker Cut Section
                Material.builder().name("Sticker Cut + Laminate").pricePerM2(6.00).build(),
                Material.builder().name("Sticker Cut No Laminate").pricePerM2(5.00).build()
            ));
            System.out.println("Materials Seeded Successfully!");
        }
    }
}