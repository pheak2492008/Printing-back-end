package com.printing_shop.Repositories;

import com.printing_shop.Enity.ProfileEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEnity, Integer> {
    Optional<ProfileEnity> findByUserId(Integer userId);
}