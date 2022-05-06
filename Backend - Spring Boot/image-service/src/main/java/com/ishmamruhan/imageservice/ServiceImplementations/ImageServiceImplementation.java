package com.ishmamruhan.imageservice.ServiceImplementations;

import com.ishmamruhan.imageservice.DAO.ImageRepo;
import com.ishmamruhan.imageservice.DTO.Image;
import com.ishmamruhan.imageservice.ExceptionManagement.CustomError;
import com.ishmamruhan.imageservice.Helpers.ImageCompressor;
import com.ishmamruhan.imageservice.Helpers.ImageDeCompresssor;
import com.ishmamruhan.imageservice.Helpers.ImageResizer;
import com.ishmamruhan.imageservice.Services.ImageService;
import com.ishmamruhan.imageservice.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class ImageServiceImplementation implements ImageService {

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private NotificationService notificationService;


    @Override
    public String saveImage(Image image)
            throws MaxUploadSizeExceededException, IOException, CustomError {

        Image mainImage = new Image();
        mainImage.setId(image.getId());
        mainImage.setImageFileName(image.getImageFileName());
        mainImage.setFileType(image.getFileType());
        mainImage.setOriginalImageData(ImageCompressor.compress(image.getOriginalImageData()));
        mainImage.setThumbnileImageData(ImageCompressor.compress(ImageResizer.resizeImage(image.getOriginalImageData())));

        notificationService.sendInfoNotification("Resize Done!");

        try{
            image = imageRepo.save(mainImage);
        }catch (Exception e){

            notificationService.sendErrorNotification("Upload Failed!");

            throw new CustomError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "Error Occured when uploading!");
        }

        notificationService.sendSuccessNotification("Upload Success!");
        return "Uploaded!";
    }

    @Override
    public String saveAllImage(List<MultipartFile> files) throws MaxUploadSizeExceededException, IOException {

        List<Image> images = files.stream()
                .map(file -> {
                    Image image = new Image();

                    image.setFileType(file.getContentType());
                    try {
                        image.setOriginalImageData(ImageCompressor.compress(file.getBytes()));
                        image.setThumbnileImageData(ImageCompressor.compress(ImageResizer.resizeImage(file)));
                    } catch (IOException e) {

                        notificationService.sendErrorNotification("Resize Failed!");

                        throw new CustomError(
                                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                "Error at 'Upload ALL' While Processing Images.",
                                "Few Images are not processable!");
                    }
                    image.setImageFileName(StringUtils.cleanPath(file.getOriginalFilename()));

                    return image;
                })
                .collect(Collectors.toList());


        try{
            imageRepo.saveAll(images);
        }catch (Exception e){

            notificationService.sendErrorNotification("Upload Failed!");

            throw new CustomError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "Error Occured when upload all image!");
        }

        return "Successfully Saved All Images!";
    }

    @Override
    public Image getImage(String id) throws CustomError{
        Image image = null;

        image = imageRepo.findById(id)
                .orElseThrow(
                        () -> new CustomError(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.toString(),
                                "Image Not Found with ID: "+id));


        image.setOriginalImageData(ImageDeCompresssor.decompress(image.getOriginalImageData()));
        image.setThumbnileImageData(ImageDeCompresssor.decompress(image.getThumbnileImageData()));

        return  image;
    }

    @Override
    public List<Image> getAllImage() {

        return imageRepo.findAll().stream()
                .map(img -> {
                    img.setOriginalImageData(ImageDeCompresssor.decompress(img.getOriginalImageData()));
                    img.setThumbnileImageData(ImageDeCompresssor.decompress(img.getThumbnileImageData()));
                    return img;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String deleteImage(String id) throws CustomError{
        imageRepo.findById(id)
                .orElseThrow(
                        () -> new CustomError(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.toString(),
                                "Image Not Found with ID: "+id));

        try{
            imageRepo.deleteById(id);
        }catch (Exception exception){

            notificationService.sendErrorNotification("Delete not possible right now!");

            throw new CustomError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "Error Occured While Deleting Image!");
        }

        return "Successfully Deleted Image";
    }
}
