package inputManager;

import commands.CommandWrapper;
import data.*;
import exceptions.*;
import exceptions.ParameterException;

import java.util.Scanner;

import static inputManager.DateConverter.parseLocalDate;

public class InputAll {
    private final Scanner scanner;

    public InputAll(Scanner sc){
        this.scanner = sc;
        scanner.useDelimiter("\n");
    }

    public Scanner getScanner(){
        return scanner;
    }

    public CommandWrapper readCommand(){
        String command = scanner.nextLine();
        if (command.contains(" ")){
            String[] arr = command.split(" ",2);
            command = arr[0];
            String arg = arr[1];
            return new CommandWrapper(command,arg);
        } else {
            return new CommandWrapper(command);
        }
    }

    public Person readPerson() throws ParameterException {
        String name = readName();
        Coordinates coordinates = readCoordinates();
        java.time.LocalDateTime createTime = readCreateTime();
        double height = readHeight();
        long weight = readWeight();
        Color hairColor = readHairColor();
        Country nationality = readNationality();
        Location location = readLocation();
        return new Person(name, coordinates, createTime, height, weight, hairColor, nationality, location );
    }

    public String readName() throws EmptyStringException {
        String s = scanner.nextLine().trim();
        if (s.isEmpty()){
            throw new EmptyStringException();
        }
        return s;
    }

    public Coordinates readCoordinates() throws InvalidNumberException {
        float x = readXCoordinate();
        float y = readYCoordinate();
        Coordinates c = new Coordinates();
        c.addCoordinates(x, y);
        return c;
    }

    public float readXCoordinate() throws InvalidNumberException {
        String s = scanner.nextLine().trim();
        float x;
        try{
            x = Float.parseFloat(s);
        }catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        if(x>-119) {
            return x;
        }else{
            throw new InvalidNumberException("x coordinate should be more than -119");
        }
    }

    public float readYCoordinate() throws InvalidNumberException{
        String s = scanner.nextLine().trim();
        float y;
        try{
            y = Float.parseFloat(s);
        }catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        if(y>-119) {
            return y;
        }else{
            throw new InvalidNumberException("y coordinate should be more than -119");
        }
    }

    public java.time.LocalDateTime readCreateTime() throws InvalidDateFormatException{
        String s = scanner.nextLine().trim();
        if(s.equals("")){
            return null;
        }
        else{
            return DateConverter.parseLocalDate(s);
        }
    }

    public double readHeight() throws InvalidNumberException {
        double height;
        try{
            height = Double.parseDouble(scanner.nextLine().trim());
        }catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        if(height>0){
            return height;
        }else{
            throw new InvalidNumberException("Height should be more than 0");
        }
    }

    public long readWeight() throws InvalidNumberException{
        long weight;
        try{
            weight = Long.parseLong(scanner.nextLine().trim());
        }catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        if(weight>0){
            return weight;
        }else{
            throw new InvalidNumberException("Weight should be more than 0");
        }
    }

    public Color readHairColor() throws InvalidEnumException {
        try{
            return Color.valueOf(scanner.nextLine().trim());
        } catch(IllegalArgumentException e){
            throw new InvalidEnumException();
        }
    }

    public Country readNationality() throws InvalidEnumException {
        try{
            return Country.valueOf(scanner.nextLine().trim());
        } catch(IllegalArgumentException e){
            throw new InvalidEnumException();
        }
    }

    public Location readLocation() throws InvalidNumberException {
        double x = readXLocation();
        long y = readYLocation();
        double z = readZLocation();
        Location location = new Location();
        location.addLocation(x,y,z);
        return location;
    }

    public double readXLocation() throws InvalidNumberException {
        try{
            return Double.parseDouble(scanner.nextLine().trim());
        }catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
    }

    public long readYLocation() throws InvalidNumberException{
        try{
            return Long.parseLong(scanner.nextLine().trim());
        }catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
    }

    public double readZLocation() throws InvalidNumberException{
        try{
            return Long.parseLong(scanner.nextLine().trim());
        }catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
    }
}
