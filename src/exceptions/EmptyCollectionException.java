package exceptions;

public class EmptyCollectionException extends CommandException{
    public EmptyCollectionException(){
        super("collection is empty");
    }
}
