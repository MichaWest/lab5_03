package exceptions;

public class RecursiveException extends FileException {
    public RecursiveException(){
        super("file is already run");
    }
}
