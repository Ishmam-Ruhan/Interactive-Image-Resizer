package com.ishmamruhan.imageservice.Controller;

import com.ishmamruhan.imageservice.DTO.Image;
import com.ishmamruhan.imageservice.ExceptionManagement.CustomError;
import com.ishmamruhan.imageservice.Helpers.Response;
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

@RestController
@CrossOrigin("http://localhost:4200")
public class MainController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/image/upload")
    public ResponseEntity<Response> saveImage(@RequestParam("file") MultipartFile multipartFile)
            throws MaxUploadSizeExceededException, IOException, CustomError {

        Image image = null;

        image = imageService
                .saveImage(multipartFile) ;

        Response response = new Response(
                image.getId(),
                image.getImageFileName(),
                " Image \""+image.getImageFileName()+"\" successfully saved with id - "+image.getId()+" at "+image.getUploadedAt());

        notificationService.sendNotification(200);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/image/upload/all")
    public ResponseEntity<String> saveImage(@RequestParam("file") List<MultipartFile> multipartFiles)
            throws MaxUploadSizeExceededException, IOException {

        imageService
                .saveAllImage(multipartFiles) ;

        notificationService.sendNotification(200);

        return ResponseEntity.status(HttpStatus.CREATED).body("All Images Saved Successfully");
    }

    @GetMapping("/image/get/all")
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getAllImage());
    }

    @GetMapping("/image/get/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable long id) {

        Image image = imageService.getImage(id);

//        return ResponseEntity.status(HttpStatus.OK).body(
//                new Image(
//                        image.getId(),
//                        image.getImageFileName(),
//                        image.getFileType(),
//                        image.getOriginalImageData(),
//                        image.getThumbnileImageData(),
//                        image.getUploadedAt()));
        return ResponseEntity.status(HttpStatus.OK).body(image);
    }

    @DeleteMapping("/image/delete/{id}")
    public ResponseEntity<String> deleteImageById(@PathVariable long id) {

        String message = imageService.deleteImage(id);

        notificationService.sendNotification("Remove Image Successfully.");

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
