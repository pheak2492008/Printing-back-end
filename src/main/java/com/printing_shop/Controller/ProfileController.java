package com.printing_shop.Controller;

import com.printing_shop.Service.ProfileService;
import com.printing_shop.dtoRequest.ProfileRequest;
import com.printing_shop.dtoRespose.ProfileResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin/profile")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getAdminProfile(@PathVariable Integer userId) {
        return ResponseEntity.ok(profileService.getAdminProfile(userId));
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProfileResponse> updateAdminProfile(
            @PathVariable Integer userId,
            @RequestPart("request") ProfileRequest request, 
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(profileService.updateAdminProfile(userId, request, image));
    }
}