package com.example.agendainteligente.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    // Método estático para convertir una cadena de fecha y hora en un objeto Date
    public static Date parseDateTime(String dateString, String timeString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            String dateTimeString = dateString + " " + timeString;
            return format.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

