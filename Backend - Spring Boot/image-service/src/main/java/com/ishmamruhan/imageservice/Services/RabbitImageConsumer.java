package com.ishmamruhan.imageservice.Services;

import com.ishmamruhan.imageservice.DTO.Image;
import org.springframework.stereotype.Service;

@Service
public interface RabbitImageConsumer {
    void consumeMessage(Image image);
}
