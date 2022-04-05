package inputManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import exceptions.InvalidDateFormatException;
/**
 * Provides methods to convenient conversion between String and Date
 */
public class DateConverter {
    private static final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    /**
     * convert LocalDate to String
     */
    public static String dateToString(LocalDateTime date){
        return date.format(localDateFormatter);
    }

    /**
     * convert LocalDate to String
     * @param s
     * @return
     * @throws InvalidDateFormatException
     */
    public static LocalDateTime parseLocalDate(String s) throws InvalidDateFormatException{
        try{
            return LocalDateTime.parse(s, localDateFormatter);
        }
        catch(java.time.format.DateTimeParseException e){
            throw new InvalidDateFormatException();
        }
    }

}