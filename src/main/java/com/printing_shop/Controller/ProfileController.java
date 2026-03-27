package com.printing_shop.Controller;

import com.printing_shop.Enity.ProfileEnity;
import com.printing_shop.Service.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // CREATE
    @PostMapping
    public ProfileEnity createProfile(@RequestBody ProfileEnity profile) {
        return profileService.createProfile(profile);
    }

    // GET by userId
    @GetMapping("/{userId}")
    public Optional<ProfileEnity> getProfile(@PathVariable Integer userId) {
        return profileService.getProfileByUserId(userId);
    }

    // UPDATE
    @PutMapping("/{userId}")
    public ProfileEnity updateProfile(
            @PathVariable Integer userId,
            @RequestBody ProfileEnity profile
    ) {
        return profileService.updateProfile(userId, profile);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteProfile(@PathVariable Integer id) {
        profileService.deleteProfile(id);
        return "Profile deleted successfully";
    }
}