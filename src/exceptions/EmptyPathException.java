package exceptions;
/**
 * class for exception caused by empty path input
 */
public class EmptyPathException extends FileException{
    public EmptyPathException(){
        super("path is empty");
    }
}
