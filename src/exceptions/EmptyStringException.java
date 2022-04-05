package exceptions;

public class EmptyStringException extends ParameterException {
    public EmptyStringException(){
        super("string cannot be empty");
    }
}
