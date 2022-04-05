package exceptions;

public class MissedCommandArgumentException extends CommandException {
    public MissedCommandArgumentException(){
        super("missed command argument");
    }
}
