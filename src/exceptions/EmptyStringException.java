package exceptions;

public class EmptyStringException extends ParameterException {
    public EmptyStringException(){
        super("String не может быть пустой");
    }
}
