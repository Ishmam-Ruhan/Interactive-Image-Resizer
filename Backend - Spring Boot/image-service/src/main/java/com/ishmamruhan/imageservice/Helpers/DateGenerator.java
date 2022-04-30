package com.ishmamruhan.imageservice.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateGenerator {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");

    public static String getDate(){
        return dateFormat.format(new Date());
    }
}
