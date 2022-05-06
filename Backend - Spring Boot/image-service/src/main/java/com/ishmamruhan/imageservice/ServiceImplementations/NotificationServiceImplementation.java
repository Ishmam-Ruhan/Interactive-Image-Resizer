package com.ishmamruhan.imageservice.ServiceImplementations;

import com.ishmamruhan.imageservice.DTO.NotificationResponse;
import com.ishmamruhan.imageservice.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Primary
public class NotificationServiceImplementation implements NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendInfoNotification(String message) {
        String content = message+"+100";
        sendMessage(content);
    }

    @Override
    public void sendSuccessNotification(String message) {
        String content = message+"+200";
        sendMessage(content);
    }

    @Override
    public void sendErrorNotification(String message) {
        String content = message+"+400";
        sendMessage(content);
    }

    void sendMessage(String message){
        messagingTemplate.convertAndSend("/topic/update",new NotificationResponse(message));
    }
}
