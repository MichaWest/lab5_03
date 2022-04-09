package exceptions;

public class MissedCommandArgumentException extends CommandException {
    public MissedCommandArgumentException(){
        super("пропущен аргумент для команды");
    }
}
