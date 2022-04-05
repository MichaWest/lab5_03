package exceptions;

public class CannotCreateFileException extends FileException{
    public CannotCreateFileException(){
        super("cannot create file");
    }
}
