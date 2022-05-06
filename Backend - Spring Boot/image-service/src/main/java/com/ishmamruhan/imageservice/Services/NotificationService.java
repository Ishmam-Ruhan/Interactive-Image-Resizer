package com.ishmamruhan.imageservice.Services;

import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    /*
     * We will send image upload status through the following service using web socket
     * Here,
     * type can be: 100 or 200 or 400
     * 1 for Successfully uploaded
     * 2 for something wrong while uploading
     * 3 for custom message
     */
     void sendInfoNotification(String message);

     void sendErrorNotification(String message);

     void sendSuccessNotification(String message);

}
