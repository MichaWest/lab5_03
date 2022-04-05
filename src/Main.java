import java.io.*;

import commands.CommandExecutor;
import fileManager.*;
import collection.*;
import inputManager.ConsoleInput;
import inputManager.InputAll;

public class Main {
    public static void main (String args[]) throws Exception{
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        FileWorker fileWorker = new FileWorker();
        PersonCollection personCollection = new PersonCollection();
        String path = "MICHELLE";
        fileWorker.setPath(path);
        if(!personCollection.deserializeCollection(fileWorker.read())){
            System.out.println("В файле ошибка, элементы не были добавлены в коллекцию");
            personCollection.clear();
        }
        InputAll console = new ConsoleInput();
        CommandExecutor command = new CommandExecutor(personCollection, console, fileWorker);
        command.consoleMode();
    }
}
