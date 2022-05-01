package com.ishmamruhan.imageservice.Services;

import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    /*
     * We will send image upload status through the following service using web socket
     * Here,
     * type can be: 1 or 2 or 3
     * 1 for Successfully uploaded
     * 2 for something wrong while uploading
     * 3 for custom message
     */

     void sendNotification(int type);

     void sendNotification(String message);
}
