package exceptions;

public class NoSuchCommandException extends CommandException{
    public NoSuchCommandException() {
        super("неправильная команда");
    }
}
