package main.java.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.UnsupportedTemporalTypeException;

/**
 * Created by ebiz on 15/03/17.
 */
public class InputCLIService {

    public static boolean isTimeStampValid(String inputString)
    {
        String str = "1986-04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(inputString, formatter);
        } catch (DateTimeParseException e){
            return false;
        }
        return true;
    }
}
