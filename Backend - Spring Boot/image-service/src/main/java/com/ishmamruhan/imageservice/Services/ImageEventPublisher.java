package com.ishmamruhan.imageservice.Services;

import com.ishmamruhan.imageservice.DTO.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageEventPublisher {
    // We'll publish a image object to queue for save the image
    void publishImageToSave(Image image);

    // We'll publish a list of image objects to queue for save the image
    void publishImageToSave(List<Image> images);

}
