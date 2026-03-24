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
        			new Material(null, "Banner Standard", 2.01),
                    new Material(null, "Banner UV", 3.01),
                    new Material(null, "Sticker China", 2.68),
                    new Material(null, "Sticker Japan", 3.18),
                    new Material(null, "Sticker UV", 3.68),
                    new Material(null, "Sticker Cut + Laminate", 6.00),
                    new Material(null, "Sticker Cut No Laminate", 5.00)
        		));
        }
    }
}