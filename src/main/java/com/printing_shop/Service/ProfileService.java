package com.printing_shop.Service;

import com.printing_shop.Enity.ProfileEnity;

import java.util.Optional;

public interface ProfileService {

    ProfileEnity createProfile(ProfileEnity profile);

    Optional<ProfileEnity> getProfileByUserId(Integer userId);

    ProfileEnity updateProfile(Integer userId, ProfileEnity profile);

    void deleteProfile(Integer id);
}