package com.ishmamruhan.imageservice.Controller;

import com.ishmamruhan.imageservice.DTO.Image;
import com.ishmamruhan.imageservice.ExceptionManagement.CustomError;
import com.ishmamruhan.imageservice.Helpers.Response;
import com.ishmamruhan.imageservice.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class MainController {
    @Autowired
    private ImageService imageService;

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

        return ResponseEntity.ok(response);
    }

    @PostMapping("/image/upload/all")
    public ResponseEntity<String> saveImage(@RequestParam("file") List<MultipartFile> multipartFiles)
            throws MaxUploadSizeExceededException, IOException {

        imageService
                .saveAllImage(multipartFiles) ;


        return ResponseEntity.ok("All Images Saved Successfully");
    }

    @GetMapping("/image/get/all")
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getAllImage());
    }

    @GetMapping("/image/get/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImage(id));
    }

    @DeleteMapping("/image/delete/{id}")
    public ResponseEntity<String> deleteImageById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.deleteImage(id));
    }

}
