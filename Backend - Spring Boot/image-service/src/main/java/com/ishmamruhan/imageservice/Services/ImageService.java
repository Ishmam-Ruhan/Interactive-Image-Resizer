package com.ishmamruhan.imageservice.Services;

import com.ishmamruhan.imageservice.DTO.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ImageService {
    // Saving single Image to Database
    Image saveImage(MultipartFile file) throws MaxUploadSizeExceededException, IOException;

    // Saving Multiple Image to Database
    String saveAllImage(List<MultipartFile> files) throws MaxUploadSizeExceededException, IOException;

    // Retrive Image by image ID
    Image getImage(long id);

    // Get All Images
    List<Image> getAllImage();

    // Delete a image by ID
    void deleteImage(long id);
}
