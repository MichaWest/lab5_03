package exceptions;

public class InvalidDateFormatException extends ParameterException {
    public InvalidDateFormatException(){
        super("date format must be HH:mm:ss");
    }
}
