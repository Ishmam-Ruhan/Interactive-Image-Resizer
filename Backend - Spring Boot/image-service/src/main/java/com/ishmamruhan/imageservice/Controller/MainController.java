package com.ishmamruhan.imageservice.Controller;

import com.ishmamruhan.imageservice.DTO.Image;
import com.ishmamruhan.imageservice.Helpers.Response;
import com.ishmamruhan.imageservice.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class MainController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Response> saveImage(@RequestParam("file") MultipartFile multipartFile)
            throws MaxUploadSizeExceededException, IOException {

        Image image = imageService.saveImage(multipartFile) ;

        Response response = new Response(image.getId(), image.getImageFileName()," Image \""+image.getImageFileName()+"\" successfully saved with id - "+image.getId()+" at "+image.getUploadedAt());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload/all")
    public ResponseEntity<String> saveImage(@RequestParam("file") List<MultipartFile> multipartFiles)
            throws MaxUploadSizeExceededException, IOException {

        imageService.saveAllImage(multipartFiles) ;

        return ResponseEntity.ok("All Images Saved Successfully");
    }

}
