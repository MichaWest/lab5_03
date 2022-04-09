package exceptions;

public class InvalidCommandArgumentException extends CommandException{
    public InvalidCommandArgumentException(String s){
        super(s);
    }
}
