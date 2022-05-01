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
    public void sendNotification(int type) {


        switch (type){
            case 200:
                String content="Successfully Uploded+200";
                messagingTemplate.convertAndSend("/topic/update",new NotificationResponse(content));
                return;

            case 400:
                String content2="Something Wrong While Uploading+400";
                messagingTemplate.convertAndSend("/topic/update",new NotificationResponse(content2));
                return;
        }
    }

    @Override
    public void sendNotification(String message) {
        String content3 = message+"+500";
        messagingTemplate.convertAndSend("/topic/update",new NotificationResponse(content3));
    }

}
