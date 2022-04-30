package com.ishmamruhan.imageservice.Helpers;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageResizer {

    public static byte[] resizeImage(MultipartFile file) throws IOException {

        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(file.getBytes());

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Thumbnails.of(inputStream)
                .size(200,200)
                .outputFormat("JPEG")
                .outputQuality(1)
                .toOutputStream(outputStream);


        return outputStream.toByteArray();
    }
}
