package exceptions;

public class InvalidNumberException extends ParameterException {
    public InvalidNumberException(String message) {
        super(message);
    }
    public InvalidNumberException(){
        super("invalid number format");
    }
}
