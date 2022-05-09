package com.ishmamruhan.imageservice.Controller;

import com.ishmamruhan.imageservice.DTO.Image;
import com.ishmamruhan.imageservice.ExceptionManagement.CustomError;
import com.ishmamruhan.imageservice.Helpers.DateGenerator;
import com.ishmamruhan.imageservice.Helpers.Response;
import com.ishmamruhan.imageservice.Services.ImageEventPublisher;
import com.ishmamruhan.imageservice.Services.ImageService;
import com.ishmamruhan.imageservice.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {

    @Autowired
    private ImageEventPublisher imageEventPublisher;

    @Autowired
    private ImageService imageService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/image/upload")
    public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile multipartFile)
            throws MaxUploadSizeExceededException, IOException, CustomError {

        Image image = new Image();
        image.setId(UUID.randomUUID().toString());
        image.setImageFileName(multipartFile.getOriginalFilename());
        image.setOriginalImageData(multipartFile.getBytes());
        image.setFileType(multipartFile.getContentType());
        image.setUploadedAt(DateGenerator.getDate());

        imageEventPublisher.publishImageToSave(image);

        notificationService.sendInfoNotification("Processing....");

        return ResponseEntity.status(HttpStatus.CREATED).body("Image Sent to processing...");
    }

    @PostMapping("/image/upload/all")
    public ResponseEntity<String> saveImage(@RequestParam("file") List<MultipartFile> multipartFiles)
            throws MaxUploadSizeExceededException, IOException {

        multipartFiles.forEach(multipartFile -> {
            Image image = new Image();
            image.setId(UUID.randomUUID().toString());
            image.setImageFileName(multipartFile.getOriginalFilename());
            try {
                image.setOriginalImageData(multipartFile.getBytes());
            } catch (IOException e) {
                throw new CustomError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),e.getMessage());
            }
            image.setFileType(multipartFile.getContentType());
            image.setUploadedAt(DateGenerator.getDate());

            imageEventPublisher.publishImageToSave(image);
        });

        notificationService.sendInfoNotification("Processing Images..");

        return ResponseEntity.status(HttpStatus.CREATED).body("All Images Sent to Process Successfully");
    }

    @GetMapping("/image/get/all")
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getAllImage());
    }

    @GetMapping("/image/get/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable String id) {

        Image image = imageService.getImage(id);

        return ResponseEntity.status(HttpStatus.OK).body(image);
    }

    @DeleteMapping("/image/delete/{id}")
    public ResponseEntity<String> deleteImageById(@PathVariable String id) {

        imageService.deleteImage(id);

        notificationService.sendErrorNotification("Remove Success!");

        return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully!");
    }

}
