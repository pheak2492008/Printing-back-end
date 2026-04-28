package com.printing_shop.Controller;

import com.printing_shop.Service.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private final CloudinaryService cloudinaryService;

    public UploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    /**
     * Upload Banner Image to Cloudinary
     */
    @Operation(summary = "Upload Banner Image to Cloudinary")
    @PostMapping(value = "/banner", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadBanner(
            @Parameter(description = "Image file to upload")
            @RequestParam("file") MultipartFile file) {

        try {
            // Upload to Cloudinary under "printing/banner" folder
            String imageUrl = cloudinaryService.uploadImage(file, "printing/banner");

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Banner image uploaded successfully",
                "imageUrl", imageUrl
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "message", "Failed to upload image: " + e.getMessage()
            ));
        }
    }

    /**
     * Upload Product Image to Cloudinary
     */
    @Operation(summary = "Upload Product Image to Cloudinary")
    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProductImage(
            @Parameter(description = "Image file to upload")
            @RequestParam("file") MultipartFile file) {

        try {
            // Upload to Cloudinary under "printing/products" folder
            String imageUrl = cloudinaryService.uploadImage(file, "printing/products");

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Product image uploaded successfully",
                "imageUrl", imageUrl
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "message", "Failed to upload image: " + e.getMessage()
            ));
        }
    }
}