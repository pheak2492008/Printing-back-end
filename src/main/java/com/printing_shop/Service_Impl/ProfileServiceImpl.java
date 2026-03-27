package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.ProfileEnity;
import com.printing_shop.Repositories.ProfileRepository;
import com.printing_shop.Service.ProfileService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ProfileEnity createProfile(ProfileEnity profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Optional<ProfileEnity> getProfileByUserId(Integer userId) {
        return profileRepository.findByUserId(userId);
    }

    @Override
    public ProfileEnity updateProfile(Integer userId, ProfileEnity updatedProfile) {
        ProfileEnity profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setFullName(updatedProfile.getFullName());
        profile.setPhone(updatedProfile.getPhone());
        profile.setAddress(updatedProfile.getAddress());
        profile.setAvatar(updatedProfile.getAvatar());

        return profileRepository.save(profile);
    }

    @Override
    public void deleteProfile(Integer id) {
        profileRepository.deleteById(id);
    }
}