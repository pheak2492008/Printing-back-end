package com.printing_shop.Repositories;

import com.printing_shop.Enity.Material;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

	Optional<Material> findById(Integer materialId);
}