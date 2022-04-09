package exceptions;

public class InvalidEnumException extends ParameterException {
    public InvalidEnumException(){
        super("неправильная константа");
    }
}
