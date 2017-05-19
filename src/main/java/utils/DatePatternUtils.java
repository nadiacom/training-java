package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatePatternUtils {

    /**
     * Check if date input is valid (expected pattern : yyyy-MM-dd).
     *
     * @param inputString (required) input string.
     * @return true if input string pattern is valid, false otherwise.
     */
    public static boolean isPatternValid(String inputString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date;
        try {
            date = LocalDate.parse(inputString, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Parse valid string into LocalDateTime pattern (yyyy-MM-dd).
     *
     * @param date (required) input string, pattern must be valid.
     * @return LocalDateTime.
     */
    public static LocalDateTime getLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDate;
        try {
            localDate = LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
        return localDate;
    }
}
