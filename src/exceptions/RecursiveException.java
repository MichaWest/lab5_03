package exceptions;

public class RecursiveException extends FileException {
    public RecursiveException(){
        super("файл уже запущен");
    }
}
