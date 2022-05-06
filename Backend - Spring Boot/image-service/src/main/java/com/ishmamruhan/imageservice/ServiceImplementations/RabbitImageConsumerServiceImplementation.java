package com.ishmamruhan.imageservice.ServiceImplementations;

import com.ishmamruhan.imageservice.DTO.Image;
import com.ishmamruhan.imageservice.ExceptionManagement.CustomError;
import com.ishmamruhan.imageservice.Services.ImageService;
import com.ishmamruhan.imageservice.Services.RabbitImageConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Primary
public class RabbitImageConsumerServiceImplementation implements RabbitImageConsumer {

    @Autowired
    private ImageService imageService;

    @Override
    @RabbitListener(queues = "${rabbit.queue.name}")
    public void consumeMessage(Image image) {
        try {
            imageService.saveImage(image);
        }catch (IOException ex){
            throw new CustomError(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                    "Error Occured while Consume Data from Rabbit!");
        }
    }
}
