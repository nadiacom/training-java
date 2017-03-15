package main.java.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by ebiz on 15/03/17.
 */
public class InputCLIService {

    public static boolean isTimeStampValid(String inputString)
    {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            format.parse(inputString);
            return true;
        }
        catch(ParseException e)
        {
            return false;
        }
    }
}
