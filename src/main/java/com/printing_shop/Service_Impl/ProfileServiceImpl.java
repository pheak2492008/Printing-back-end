package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.ProfileEnity;
import com.printing_shop.Repositories.ProfileRepository;
import com.printing_shop.Service.ProfileService;
import com.printing_shop.dtoRequest.ProfileRequest;
import com.printing_shop.dtoResponse.ProfileResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final String UPLOAD_DIRECTORY = "uploads";

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ProfileResponse getAdminProfile(Integer userId) {
        ProfileEnity profile = profileRepository.findByUserId(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("Admin Profile not found"));
        return mapToResponse(profile);
    }

    @Override
    public ProfileResponse updateAdminProfile(Integer userId, ProfileRequest request, MultipartFile image) throws IOException {
        // 1. Fetch the existing record from the database
        ProfileEnity profile = profileRepository.findByUserId(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("Admin Profile not found for ID: " + userId));

        // 2. PARTIAL TEXT UPDATES
        // We check if the field is present in the 'request' object. 
        // If the admin didn't change it, and your frontend sends the old value, this still works.
        // If your frontend sends null for a specific field, the 'if' prevents overwriting.
        if (request.getFullName() != null && !request.getFullName().trim().isEmpty()) {
            profile.setFullName(request.getFullName());
        }
        
        if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            profile.setPhone(request.getPhone());
        }
        
        if (request.getAddress() != null && !request.getAddress().trim().isEmpty()) {
            profile.setAddress(request.getAddress());
        }

        // 3. INDIVIDUAL IMAGE UPDATE
        // Because 'required = false' in the Controller, 'image' might be null.
        if (image != null && !image.isEmpty()) {
            // Ensure directory exists
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename to avoid overwriting files with same name
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            
            // Save file to disk
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Update the entity with the new path
            // The "/uploads/" prefix allows your WebConfig to serve it as a static resource
            profile.setAvatar("/uploads/" + fileName);
        }

        // 4. Save and return the updated entity
        ProfileEnity savedProfile = profileRepository.save(profile);
        return mapToResponse(savedProfile);
    }

    private ProfileResponse mapToResponse(ProfileEnity entity) {
        return ProfileResponse.builder()
                .userId(entity.getUserId().intValue())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .avatarUrl(entity.getAvatar())
                .build();
    }
}