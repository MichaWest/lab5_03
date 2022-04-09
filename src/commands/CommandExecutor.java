package commands;

import collection.PersonCollection;
import data.Color;
import exceptions.*;
import fileManager.FileWorker;
import inputManager.*;

import java.util.*;

public class CommandExecutor {
    private final PersonCollection collection;
    private InputAll input;
    private final FileWorker fileWorker;
    private boolean run;
    private final Stack<String> runFiles;
    private final List<String> history;
    private String currentScriptFileName;
    private final String[] commands= {"help","info", "show", "add", "remove_by_id", "update_by_id", "clear", "save", "execute_script", "exit", "remove_first", "reorder", "history", "group_counting_by_nationality", "count_by_hair_color" };

    public CommandExecutor(PersonCollection cManager, InputAll iManager, FileWorker fManager){
        this.collection = cManager;
        this.input = iManager;
        this.fileWorker = fManager;
        runFiles = new Stack<>();
        history = new ArrayList<>();
    }

    public void consoleMode(){
        input = new ConsoleInput();
        run = true;
        while(run){
            System.out.print("Введите команду (введиет help, чтобы получить список комманд): ");
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
            if (!hasCommand(s)) throw new NoSuchCommandException();
            getCommand(s, arg);
        }
        catch(CommandException | ParameterException e){
            System.out.println(e.getMessage());
        } catch (RecursiveException e) {
            e.printStackTrace();
        }
    }

    private boolean hasCommand(String s){
        for(String i: commands){
            if(s.equals(i)){
                return true;
            }
        }
        return false;
    }

    private void getCommand(String Command, String arg) throws ParameterException, RecursiveException {
        switch(Command){
            case("help"):
                Help help = new Help();
                help.run(arg);
                break;
            case("info"):
                Info info = new Info();
                info.run(arg);
                break;
            case("show"):
                Show show = new Show();
                show.run(arg);
                break;
            case("add"):
                Add add = new Add();
                add.run(arg);
                break;
            case("remove_by_id"):
                RemoveById removeById = new RemoveById();
                removeById.run(arg);
                break;
            case("update_by_id"):
                UpdateById updateById = new UpdateById();
                updateById.run(arg);
                break;
            case("clear"):
                Clear clear = new Clear();
                clear.run(arg);
                break;
            case("save"):
                Save save = new Save();
                save.run(arg);
                break;
            case("execute_script"):
                ExecuteScript executeScript = new ExecuteScript();
                executeScript.run(arg);
                break;
            case("remove_first"):
                RemoveFirst removeFirst = new RemoveFirst();
                removeFirst.run(arg);
                break;
            case("exit"):
                Exit exit = new Exit();
                exit.run(arg);
                break;
            case("reorder"):
                Reorder reorder = new Reorder();
                reorder.run(arg);
                break;
            case("history"):
                History history = new History();
                history.run(arg);
                break;
            case("min_by_weight"):
                MinByWeight minByWeight = new MinByWeight();
                minByWeight.run(arg);
                break;
            case("group_counting_by_nationality"):
                GroupCountingByNationality groupCountingByNationality = new GroupCountingByNationality();
                groupCountingByNationality.run(arg);
                break;
            case("count_by_hair_color"):
                CountByHairColor countByHairColor = new CountByHairColor();
                countByHairColor.run(arg);
                break;
        }
    }

    public class Help implements Command{
        public void run(String arg){
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
            history.add("help");
        }
    }

    public class Info implements Command{
        public void run(String arg){
            if (collection.getCollection()==null) throw new EmptyCollectionException();
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            System.out.println(collection.getInfo());
            history.add("info");
        }
    }

    public class Show implements Command{
        public void run(String arg){
            if (collection.getCollection()==null) throw new EmptyCollectionException();
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            System.out.println(collection.serializeCollection());
            history.add("show");
        }
    }

    public class Add implements Command{
        public void run(String arg) throws ParameterException {
            collection.add(input.readPerson());
            history.add("add");
        }
    }

    public class RemoveById implements Command{
        public void run(String arg){
            int id;
            if(arg==null||arg.isEmpty()){
                throw new MissedCommandArgumentException();
            }
            try{
                id = Integer.parseInt(arg);
            }catch(NumberFormatException e){
                throw new InvalidCommandArgumentException("id должен быть типа int");
            }
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            if (!collection.checkId(id)) throw new InvalidCommandArgumentException("нет такого id");
            collection.removeById(id);
            history.add("remove_by_id");
            System.out.println("Элемент с id "+id+" успешно удален");
        }
    }

    public class UpdateById implements Command{
        public void run(String arg) throws ParameterException {
            int id;
            if (arg == null || arg.equals("")){
                throw new MissedCommandArgumentException();
            }
            try{
                id = Integer.parseInt(arg);
            } catch (NumberFormatException e){
                throw new InvalidCommandArgumentException("id должен быть типа int");
            }
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            if (!collection.checkId(id)) throw new InvalidCommandArgumentException("нет такого id");

            collection.updateById(id, input.readPerson());
            history.add("update_by_id "+arg);
        }
    }

    public class Clear implements Command{
        public void run(String arg){
            collection.clear();
            history.add("clear");
            System.out.println("Коллекция очищена");
        }
    }

    public class Save implements Command{
        public void run(String arg){
            if (collection.getCollection().isEmpty()) System.out.println("collection is empty");
            if(!fileWorker.write(collection.serializeCollection())) throw new CommandException("cannot save collection");
            history.add("save");
            System.out.println("Коллекция успешно сохранена");
        }
    }

    public class ExecuteScript implements Command{
        public void run(String arg) throws RecursiveException {
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
        }
    }

    public class RemoveFirst implements Command{
        public void run(String arg){
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            collection.remove(0);
            history.add("remove_first");
        }
    }

    public class Exit implements Command{
        public void run(String arg){
            run = false;
            history.add("exit");
        }
    }

    public class Reorder implements Command{
        public void run(String arg){
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            collection.reorder();
            history.add("reorder");
            System.out.println("Коллекция успешно пересортировалась");
        }
    }

    public class History implements Command{
        public void run(String arg){
            getHistory();
            history.add("history");
        }

        private void getHistory(){
            if(history.isEmpty()){
                System.out.println("History is empty");
            }else{
                for(int i = history.size()-1;(i>=0)&&(i>history.size()-7);i--){
                    System.out.println(history.get(i));
                }
            }
        }

    }

    public class MinByWeight implements Command{
        public void run(String arg){
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            System.out.println(collection.minByWeight().getName());
            history.add("min_by_weight");
        }
    }

    public class GroupCountingByNationality implements Command{
        public void run(String arg) throws InvalidEnumException {
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            PersonCollection group = collection.groupByNationality(input.readNationality());
            if(group.getCollection().isEmpty()){
                System.out.println("group with this nationality doesn't exist");
            }else{
                System.out.println(group.printCollection());
            }
            history.add("group_counting_by_nationality");
        }
    }

    public class CountByHairColor implements Command{
        public void run(String arg) throws InvalidEnumException {
            int count;
            if (collection.getCollection().isEmpty()) throw new EmptyCollectionException();
            if(arg==null) throw new MissedCommandArgumentException();
            try{
                count = collection.countByHairColor(Color.valueOf(arg));
            } catch(IllegalArgumentException e){
                throw new InvalidEnumException();
            }
            System.out.println("Count by hair with "+arg+" color: "+count);
        }
    }
}
