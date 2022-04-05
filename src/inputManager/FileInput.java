package inputManager;

import fileManager.FileWorker;

import java.util.Scanner;

public class FileInput extends InputAll {
    public FileInput(String path){
        super(new Scanner(new FileWorker(path).read()));
        getScanner().useDelimiter("\n");
    }
}
