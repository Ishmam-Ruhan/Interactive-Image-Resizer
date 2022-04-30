package com.ishmamruhan.imageservice.ServiceImplementations;

import com.ishmamruhan.imageservice.DAO.ImageRepo;
import com.ishmamruhan.imageservice.DTO.Image;
import com.ishmamruhan.imageservice.ExceptionManagement.CustomError;
import com.ishmamruhan.imageservice.Helpers.ImageResizer;
import com.ishmamruhan.imageservice.Services.ImageService;
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

    @Override
    public Image saveImage(MultipartFile file)
            throws MaxUploadSizeExceededException, IOException, CustomError {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Image image = new Image();

        image.setFileType(file.getContentType());
        image.setOriginalImageData(file.getBytes());
        image.setThumbnileImageData(ImageResizer.resizeImage(file));
        image.setImageFileName(fileName);

        try{
            image = imageRepo.save(image);
        }catch (Exception e){
            throw new CustomError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "Error Occured when uploading!");
        }

        return image;
    }

    @Override
    public String saveAllImage(List<MultipartFile> files) throws MaxUploadSizeExceededException, IOException {

        List<Image> images = files.stream()
                .map(file -> {
                    Image image = new Image();

                    image.setFileType(file.getContentType());
                    try {
                        image.setOriginalImageData(file.getBytes());
                        image.setThumbnileImageData(ImageResizer.resizeImage(file));
                    } catch (IOException e) {
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
            throw new CustomError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "Error Occured when upload all image!");
        }

        return "Successfully Saved All Images!";
    }

    @Override
    public Image getImage(long id) throws CustomError{
        Image image = null;

        image = imageRepo.findById(id)
                .orElseThrow(
                        () -> new CustomError(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.toString(),
                                "Image Not Found with ID: "+id));

        return  image;
    }

    @Override
    public List<Image> getAllImage() {
        return imageRepo.findAll();
    }

    @Override
    public String deleteImage(long id) throws CustomError{
        imageRepo.findById(id)
                .orElseThrow(
                        () -> new CustomError(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.toString(),
                                "Image Not Found with ID: "+id));

        try{
            imageRepo.deleteById(id);
        }catch (Exception exception){
            throw new CustomError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "Error Occured While Deleting Image!");
        }

        return "Successfully Deleted Image";
    }
}
