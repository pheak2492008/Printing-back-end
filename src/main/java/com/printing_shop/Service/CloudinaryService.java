package com.printing_shop.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file, String folderName) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folderName != null ? folderName : "printing",
                "resource_type", "image"
        );

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

        return (String) uploadResult.get("secure_url");
    }
}