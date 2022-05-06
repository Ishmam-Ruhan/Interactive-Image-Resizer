package com.ishmamruhan.imageservice.ServiceImplementations;

import com.ishmamruhan.imageservice.Configurations.RabbitPublisherConfiguration;
import com.ishmamruhan.imageservice.DTO.Image;
import com.ishmamruhan.imageservice.Services.ImageEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ImageEventPublisherServiceImplementation implements ImageEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitPublisherConfiguration config;
    @Override
    public void publishImageToSave(Image image) {
        rabbitTemplate.convertAndSend(config.getEXCHANGE(),config.getROUTING_KEY(),image);
    }

    @Override
    public void publishImageToSave(List<Image> images) {
        images.forEach(image -> {
            rabbitTemplate.convertAndSend(config.getEXCHANGE(),config.getROUTING_KEY(),image);
        });
    }

}
