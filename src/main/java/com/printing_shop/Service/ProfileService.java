package com.printing_shop.Service;

import com.printing_shop.dtoRequest.ProfileRequest;
import com.printing_shop.dtoRespose.ProfileResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ProfileService {
    ProfileResponse getAdminProfile(Integer userId);
    ProfileResponse updateAdminProfile(Integer userId, ProfileRequest request, MultipartFile image) throws IOException;
}