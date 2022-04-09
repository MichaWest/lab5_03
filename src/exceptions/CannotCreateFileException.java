package exceptions;

public class CannotCreateFileException extends FileException{
    public CannotCreateFileException(){
        super("немогу создать файл");
    }
}
