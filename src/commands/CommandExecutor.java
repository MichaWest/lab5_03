package commands;

import collection.PersonCollection;
import data.Color;
import exceptions.*;
import fileManager.FileWorker;
import inputManager.*;

import java.util.*;

public class CommandExecutor {
    private final Map<String,Command> map;
    private final PersonCollection collection;
    private InputAll input;
    private final FileWorker fileWorker;
    private boolean run;
    private final Stack<String> runFiles;
    private final List<String> history;
    private String currentScriptFileName;

    public CommandExecutor(PersonCollection cManager, InputAll iManager, FileWorker fManager){
        this.collection = cManager;
        this.input = iManager;
        this.fileWorker = fManager;
        map = new HashMap<>();
        runFiles = new Stack<>();
        history = new ArrayList<>();
        addCommand("help", (a)->{
            getHelp();
            history.add("help");
        });
        addCommand("info",(a)-> {
            System.out.println(collection.getInfo());
            history.add("info");
        });
        addCommand("show", (a)->{
            if (collection.getCollection().isEmpty()){
                System.out.println("collection is empty");
            }
            else {
                System.out.println(collection.serializeCollection());
            }
            history.add("show");
        });
        addCommand("add", (a)->{
            collection.add(input.readPerson());
            history.add("add");
        });
        addCommand("remove_by_id", (arg)->{
            int id;
            if(arg==null||arg.isEmpty()){
                throw new MissedCommandArgumentException();
            }
            try{
                id = Integer.parseInt(arg);
            }catch(NumberFormatException e){
                throw new InvalidCommandArgumentException("id should be Integer");
            }
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            if (collection.checkId(id)) throw new InvalidCommandArgumentException("no such id");
            collection.removeById(id);
            history.add("remove_by_id");
        });
        addCommand("update_by_id", (arg)->{
            int id;
            if (arg == null || arg.equals("")){
                throw new MissedCommandArgumentException();
            }
            try{
                id = Integer.parseInt(arg);
            } catch (NumberFormatException e){
                throw new InvalidCommandArgumentException("id must be integer");
            }
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            if (collection.checkId(id)) throw new InvalidCommandArgumentException("no such id");

            collection.updateById(id, input.readPerson());
            history.add("update_by_id "+arg);
        });
        addCommand("clear", (a)->{
            collection.clear();
            history.add("clear");
        });
        addCommand("save", (a)->{
            if (collection.getCollection().isEmpty()) System.out.println("collection is empty");
            if(!fileWorker.write(collection.serializeCollection())) throw new CommandException("cannot save collection");
            history.add("save");
        });
        addCommand("execute_script", (arg)->{
            if(arg==null||arg.isEmpty()){
                throw new MissedCommandArgumentException();
            }
            if(runFiles.contains(arg)) throw new RecursiveException();
            runFiles.push(currentScriptFileName);
            CommandExecutor process = new CommandExecutor(collection, input, fileWorker);
            process.fileMode(arg);
            runFiles.pop();
            System.out.println("successfully executed script " + arg);
            history.add("execute_script");
        });
        addCommand("remove_first",(a)->{
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            collection.remove(0);
            history.add("remove_first");
        });
        addCommand("exit",(a)->{
            run = false;
            history.add("exit");
        });
        addCommand("reorder", (a)->{
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            collection.reorder();
        });
        addCommand("history",(a)-> {
            getHistory();
            history.add("history");
        });
        addCommand("min_by_weight",(a)->{
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            System.out.println(collection.minByWeight());
            history.add("min_by_weight");
        });
        addCommand("group_counting_by_nationality", (a)->{
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            PersonCollection group = collection.groupByNationality(input.readNationality());
            if(group.getCollection().isEmpty()){
                System.out.println("group with this nationality doesn't exist");
            }else{
                System.out.println(group.printCollection());
            }
            history.add("group_counting_by_nationality");
        });
        addCommand("count_by_hair_color", (arg)->{
            int count;
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            try{
                count = collection.countByHairColor(Color.valueOf(arg));
            } catch(IllegalArgumentException e){
                throw new InvalidEnumException();
            }
            System.out.println("Count by hair with "+arg+" color: "+count);
        });
    }

    public void addCommand(String key, Command c){
        map.put(key, c);
    }

    public void consoleMode(){
        input = new ConsoleInput();
        run = true;
        while(run){
            System.out.print("Enter command (enter help to get list command");
            CommandWrapper cmd = input.readCommand();
            runCommand(cmd.getCom(), cmd.getArg());
        }
    }

    public void fileMode(String path){
        currentScriptFileName = path;
        input = new FileInput(path);
        run = true;
        while(run && input.getScanner().hasNextLine()){
            CommandWrapper cmd = input.readCommand();
            runCommand(cmd.getCom(), cmd.getArg());
        }
    }

    public void runCommand(String s, String arg){
        try{
            if (! hasCommand(s)) throw new NoSuchCommandException();
            map.get(s).run(arg);
        }
        catch(CommandException | ParameterException e){
            System.out.println(e.getMessage());
        } catch (RecursiveException e) {
            e.printStackTrace();
        }
    }

    public boolean hasCommand(String s){
        return map.containsKey(s);
    }

    public void getHelp(){
        System.out.println("""
                help : вывести справку по доступным командам
                info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                add {element} : добавить новый элемент в коллекцию
                update id {element} : обновить значение элемента коллекции, id которого равен заданному
                remove_by_id id : удалить элемент из коллекции по его id
                clear : очистить коллекцию
                save : сохранить коллекцию в файл
                execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                exit : завершить программу (без сохранения в файл)
                remove_first : удалить первый элемент из коллекции
                reorder : отсортировать коллекцию в порядке, обратном нынешнему
                history : вывести последние 7 команд (без их аргументов)
                min_by_weight : вывести любой объект из коллекции, значение поля weight которого является минимальным
                group_counting_by_nationality : сгруппировать элементы коллекции по значению поля nationality, вывести количество элементов в каждой группе
                count_by_hair_color hairColor : вывести количество элементов, значение поля hairColor которых равно заданному""");
    }

    public void getHistory(){
        if(history.isEmpty()){
            System.out.println("History is empty");
        }else{
            for(int i = history.size()-1;(i>=0)||(i>history.size()-7);i--){
                System.out.println(history.get(i));
            }
        }
    }
}
